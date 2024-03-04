package com.olympic.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.olympic.model.repo.AdminRepo;

public class AdminAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AdminRepo repo;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String pass = authentication.getCredentials().toString();
		
		var opt = repo.findByName(name);
		
		if(opt.isEmpty()) {
			throw new BadCredentialsException("Admin Name Not Found.");
		}
		
		var admin = opt.get();
		
		if(admin.getName().equals(name) && admin.getPassword().equals(pass)) {
			List<GrantedAuthority> list = new ArrayList<>();
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			return new UsernamePasswordAuthenticationToken(name, pass, list);
		} else {
			throw new BadCredentialsException("Wrong Password.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
