package com.abdelaziz.service;

import java.util.List;

import com.abdelaziz.model.Role;

public interface UserRolesService {
	public void addUserRoles(Role userRoles);

	public void updateUserRoles(Role userRoles);

	public Role findUserRolesById(long id);

	public List<Role> findAllUserRoless();

	public void deleteUserRoles(Role userRoles);

	public void deleteUserRolesById(long id);
}
