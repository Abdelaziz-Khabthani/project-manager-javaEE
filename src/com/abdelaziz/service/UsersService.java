package com.abdelaziz.service;

import java.util.List;
import com.abdelaziz.model.Users;

public interface UsersService {
	public void addUsers(Users users);

	public void updateUsers(Users users);

	public Users findUsersById(long id);

	public List<Users> findAllUserss();

	public void deleteUsers(Users users);

	public void deleteUsersById(long id);
	
	public Users findUserByUserName(String username);
}
