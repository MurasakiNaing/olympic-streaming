package com.olympic.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	private String id;
	
	@ManyToOne
	private Country country;
	
	@ManyToMany
	@JoinTable(
			name = "preferred_sport",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "sport_id")
			)
	private List<Sport> preferredSports;
	
	private String email;
	private String password;
	private boolean activated;
	private String role;
	private String imageName;
	private String name;
	private String phone;

	public User() {
		this.role = "USER";
		this.preferredSports = new ArrayList<>();
		this.imageName = "default_pfp.png";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authList;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return activated;
	}
}
