package com.abdelaziz.service.impl;

import java.util.List;

import com.abdelaziz.dao.UserRolesDao;
import com.abdelaziz.model.Role;
import com.abdelaziz.service.UserRolesService;

public class UserRolesServiceImpl implements UserRolesService {

	private UserRolesDao userRolesDao;

	public void setUserRolesDao(UserRolesDao userRolesDao) {
		this.userRolesDao = userRolesDao;
	}

	@Override
	public void addUserRoles(Role userRoles) {
		userRolesDao.create(userRoles);
	}

	@Override
	public void updateUserRoles(Role userRoles) {
		userRolesDao.update(userRoles);
	}

	@Override
	public Role findUserRolesById(long id) {
		return userRolesDao.findById(id);
	}

	@Override
	public List<Role> findAllUserRoless() {
		return userRolesDao.findAll();
	}

	@Override
	public void deleteUserRoles(Role userRoles) {
		userRolesDao.delete(userRoles);
	}

	@Override
	public void deleteUserRolesById(long id) {
		userRolesDao.deleteById(id);
	}
}
