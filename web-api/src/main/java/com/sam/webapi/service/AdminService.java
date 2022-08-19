package com.sam.webapi.service;

import com.sam.webapi.dto.AdminDto;

public interface AdminService {
	AdminDto getAdmin(Integer id, String adminEmail);
	void updateAdmin(Integer id, AdminDto admin, String adminEmail);
}
