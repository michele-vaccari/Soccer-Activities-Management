package com.sam.webapi.service;

import com.sam.webapi.dataaccess.AdminUserRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {

	private final AdminUserRepository adminUserRepository;
	private final UserRepository userRepository;

	@Autowired
	public AdminServiceImpl(AdminUserRepository adminUserRepository,
							UserRepository userRepository) {
		this.adminUserRepository = adminUserRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public AdminDto getAdmin(Integer id, String adminEmail) {
		isAuthorizedUser(adminEmail);

		var admin = adminUserRepository.findById(id);

		if (admin.isEmpty())
			throw new AdminUserNotFoundException();

		return convertEntityToDto(admin.get());
	}

	@Override
	@Transactional
	public void updateAdmin(Integer id, AdminDto adminDto, String adminEmail) {
		isAuthorizedUser(adminEmail);

		var user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException();

		var admin = adminUserRepository.findById(id);
		if (admin.isEmpty())
			throw new AdminUserNotFoundException();

		if (userRepository.existsByEmailAndIdNot(adminDto.getEmail(), id))
			throw new SingleEmailConstraintException();

		if (adminDto.getName() != null && !adminDto.getName().isEmpty())
			user.get().setName(adminDto.getName());
		if (adminDto.getSurname() != null && !adminDto.getSurname().isEmpty())
			user.get().setSurname(adminDto.getSurname());
		if (adminDto.getEmail() != null && !adminDto.getEmail().isEmpty())
			user.get().setEmail(adminDto.getEmail());
		if (adminDto.getPassword() != null && !adminDto.getPassword().isEmpty())
			user.get().setPassword(adminDto.getPassword());
		if (adminDto.getActive() != null && !adminDto.getActive().isEmpty())
			user.get().setActive(adminDto.getActive());

		userRepository.save(user.get());
		adminUserRepository.save(admin.get());
	}

	private void isAuthorizedUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
			!user.getRole().equals("Admin"))
			throw new UnauthorizedException();
	}

	private AdminDto convertEntityToDto(AdminUser admin) {
		var user =  admin.getUserById();
		return new AdminDto(
				user.getId(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				null,
				user.getActive()
		);
	}
}
