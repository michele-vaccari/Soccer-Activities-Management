package com.sam.webapi.service;

import com.sam.webapi.dto.AdminDto;

import java.util.Optional;

public interface AdminService {
	Iterable<AdminDto> getAdmins(String adminEmail);
	AdminDto getAdmin(Integer id, String adminEmail);
	void createAdmin(String adminEmail, AdminDto admin);
	void updateAdmin(Integer id, AdminDto admin, String adminEmail);
	void deleteAdmin(Integer id, String adminEmail);
}
