package com.abdelaziz.dao;

import com.abdelaziz.model.Users;

public interface UsersDao extends GenericDao<Users, Long> {

	public Users findUserByUsername(String username);

}
