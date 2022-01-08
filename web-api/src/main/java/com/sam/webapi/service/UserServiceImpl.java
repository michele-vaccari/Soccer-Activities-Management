package com.sam.webapi.service;

import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<User> getUser(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public void createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new SingleEmailConstraintException();

		var userId = userRepository.getMaxId();
		user.setId(++userId);
		user.setActive("Y");

		userRepository.save(user);
	}

	@Override
	@Transactional
	public void updateUser(Integer id, User user) {
		if (!userRepository.existsById(id))
			throw new UserNotFoundException();

		if (userRepository.existsByEmailAndIdNot(user.getEmail(), id))
			throw new SingleEmailConstraintException();

		user.setId(id);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		if (!userRepository.existsById(id))
			throw new UserNotFoundException();

		userRepository.deactivateUserById(id);
	}
}
