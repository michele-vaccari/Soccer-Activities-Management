package com.sam.webapi.service;

import com.sam.webapi.dto.RefereeDto;
import com.sam.webapi.dto.ReportDto;

public interface RefereeService {
	Iterable<RefereeDto> getReferees(String adminEmail);
	RefereeDto getReferee(Integer id, String adminEmail);
	Iterable<ReportDto> getReportsOfReferee(Integer id, String refereeEmail);
	void createReferee(String adminEmail, RefereeDto referee);
	void updateReferee(Integer id, RefereeDto referee, String adminEmail);
	void deleteReferee(Integer id, String adminEmail);
}
