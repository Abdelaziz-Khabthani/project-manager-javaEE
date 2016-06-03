package com.abdelaziz.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abdelaziz.model.Role;
import com.abdelaziz.model.Users;
import com.abdelaziz.service.UsersService;

public class UserLoginService implements UserDetailsService {

	private UsersService usersService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users user = usersService.findUserByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User " + username
					+ " not found.");
		}

		String password = user.getUsersPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<Role> roles = new ArrayList<Role>(user.getRoles());

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleLabel()));
		}

		return new User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
}
