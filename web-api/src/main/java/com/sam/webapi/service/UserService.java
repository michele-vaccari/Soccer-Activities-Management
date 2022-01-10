package com.sam.webapi.service;

import com.sam.webapi.model.User;

import java.util.Optional;

public interface UserService {
	public abstract User getActiveUserByEmail(String email);
	public abstract Iterable<User> getUsers();
	public abstract Optional<User> getUser(Integer id);
	public abstract void createUser(User user);
	public abstract void updateUser(Integer id, User user);
	public abstract void deleteUser(Integer id);
}
