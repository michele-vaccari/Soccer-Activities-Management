package com.sam.webapi.service;

import com.sam.webapi.model.User;

import java.util.Optional;

public interface UserService {
	public abstract Iterable<User> getUsers();
	public abstract Optional<User> getUser(Integer id);
	public abstract void createUser(User user);
	public abstract void updateUser(User user);
	public abstract void deleteUser(Integer id);
}
