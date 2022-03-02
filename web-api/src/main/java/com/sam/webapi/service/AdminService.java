package com.sam.webapi.service;

import com.sam.webapi.dto.AdminDto;

import java.util.Optional;

public interface AdminService {
	Iterable<AdminDto> getAdmins();
	Optional<AdminDto> getAdmin(Integer id);
	void createAdmin(String adminEmail, AdminDto admin);
	void updateAdmin(Integer id, AdminDto admin);
	void deleteAdmin(Integer id);
}
