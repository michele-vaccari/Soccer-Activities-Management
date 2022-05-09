package com.sam.webapi.service;

import com.sam.webapi.dto.RefereeDto;

public interface RefereeService {
	Iterable<RefereeDto> getReferees(String adminEmail);
	RefereeDto getReferee(Integer id, String adminEmail);
	void createReferee(String adminEmail, RefereeDto referee);
	void updateReferee(Integer id, RefereeDto referee, String adminEmail);
	void deleteReferee(Integer id, String adminEmail);
}
