package com.sam.webapi.service;

import com.sam.webapi.dto.AdminDto;

import java.util.Optional;

public interface AdminService {
	public abstract Iterable<AdminDto> getAdmins();
	public abstract Optional<AdminDto> getAdmin(Integer id);
	public abstract void createAdmin(String adminEmail, AdminDto admin);
	public abstract void updateAdmin(Integer id, AdminDto admin);
	public abstract void deleteAdmin(Integer id);
}
