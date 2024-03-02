package com.olympic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.olympic.model.repo.UserRepo;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
		return builder.build();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/hello/**").hasRole("USER");
			auth.anyRequest().authenticated();
		});
		
		http.formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
}
