
package com.example.jwt.entity.jwt_related;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jwt.entity.User;

public class UserDetailsImpl implements UserDetails {

	private String email;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(final User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();

		List<String> roleNames = user.getRoles().stream()
				.map(r -> r.getRoleName().name())
				.toList();
		this.authorities = roleNames.stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		for (GrantedAuthority s : authorities) {
			System.out.println(s.getAuthority());
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
