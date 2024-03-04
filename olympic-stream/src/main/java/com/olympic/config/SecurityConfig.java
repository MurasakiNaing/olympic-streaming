package com.olympic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.olympic.event.handler.CustomAuthenticationSuccessHandler;
import com.olympic.model.repo.UserRepo;
import com.olympic.provider.AdminAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AdminAuthenticationProvider adminProvider() {
		return new AdminAuthenticationProvider();
	}

	@Bean
	SecurityFilterChain resourcesFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/resources/**").authorizeHttpRequests(request -> {
			request.anyRequest().permitAll();
		});

		return http.build();
	}

	@Bean
	SecurityFilterChain homeFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/").authorizeHttpRequests(req -> {
			req.anyRequest().permitAll();
		});
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepo repo) {
		return username -> {
			return repo.findByemail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
		};
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder, UserDetailsService userDetailsService) {
		var dap = new DaoAuthenticationProvider();
		dap.setPasswordEncoder(encoder);
		dap.setUserDetailsService(userDetailsService);
		return dap;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.authenticationProvider(daoAuthenticationProvider);
		builder.authenticationProvider(adminProvider());
		return builder.build();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager, HandlerMappingIntrospector introspector) throws Exception {
		
		var mvc = new MvcRequestMatcher.Builder(introspector);
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers(mvc.pattern("/auth/**")).permitAll();
			auth.requestMatchers(mvc.pattern("/admin/**")).hasRole("ADMIN");
			auth.requestMatchers(mvc.pattern("/user/**")).hasAnyRole("USER", "ADMIN");
			auth.anyRequest().authenticated();
		});
		
		http.formLogin(form -> {
			form.loginPage("/auth/authenticate");
			form.successHandler(successHandler());
		});
		
		http.logout(logout -> {
			logout.logoutUrl("/logout");
		});
		
		http.authenticationManager(authManager);
		
		http.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
	}

}
