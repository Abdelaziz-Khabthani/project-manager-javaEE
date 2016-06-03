package com.abdelaziz.backing;

import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.abdelaziz.service.UsersService;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{usersService}")
	private UsersService usersService;

	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;

	private String username;
	private String password;
	private String fullUserName;
	private Set<String> roles;

	public String login() {
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					username, password);
			Authentication authenticate = authenticationManager
					.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authenticate);

			this.fullUserName = usersService.findUserByUserName(username)
					.getUsersFullName();

			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			roles = AuthorityUtils.authorityListToSet(userDetails
					.getAuthorities());

			return "correct";
		} catch (final Exception e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Wrong Username/Password Combination Please Try Again.",
									null));
		}
		return null;
	}

	public String logOut() {
		SecurityContextHolder.clearContext();
		clearSession();
		return "logout";
	}

	private void clearSession() {
		this.username = null;
		this.password = null;
		this.fullUserName = null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullUserName() {
		return fullUserName;
	}

	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
