package com.abdelaziz.service.impl;

import java.util.List;

import com.abdelaziz.dao.UsersDao;
import com.abdelaziz.model.Users;
import com.abdelaziz.service.UsersService;

public class UsersServiceImpl implements UsersService {

	private UsersDao usersDao;

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Override
	public void addUsers(Users users) {
		usersDao.create(users);
	}

	@Override
	public void updateUsers(Users users) {
		usersDao.update(users);
	}

	@Override
	public Users findUsersById(long id) {
		return usersDao.findById(id);
	}

	@Override
	public List<Users> findAllUserss() {
		return usersDao.findAll();
	}

	@Override
	public void deleteUsers(Users users) {
		usersDao.delete(users);
	}

	@Override
	public void deleteUsersById(long id) {
		usersDao.deleteById(id);
	}

	@Override
	public Users findUserByUserName(String username) {
		return usersDao.findUserByUsername(username);
	}
}
