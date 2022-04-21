package com.sam.webapi.service;

import com.sam.webapi.dto.RefereeDto;

public interface RefereeService {
	Iterable<RefereeDto> getReferees();
	RefereeDto getReferee(Integer id);
	void createReferee(String adminEmail, RefereeDto referee);
	void updateReferee(Integer id, RefereeDto referee);
	void deleteReferee(Integer id);
}
