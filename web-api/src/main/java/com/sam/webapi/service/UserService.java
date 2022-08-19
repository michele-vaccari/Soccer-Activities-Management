package com.sam.webapi.service;

import com.sam.webapi.model.User;

import java.util.Optional;

public interface UserService {
	User getActiveUserByEmail(String email);
	Iterable<User> getUsers();
	Optional<User> getUser(Integer id);
	void createUser(User user);
	void updateUser(Integer id, User user);
	void deleteUser(Integer id);
}
