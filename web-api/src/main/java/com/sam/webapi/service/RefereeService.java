package com.sam.webapi.service;

import com.sam.webapi.dto.RefereeDto;

import java.util.Optional;

public interface RefereeService {
	public abstract Iterable<RefereeDto> getReferees();
	public abstract Optional<RefereeDto> getReferee(Integer id);
	public abstract void createReferee(String adminEmail, RefereeDto referee);
	public abstract void updateReferee(Integer id, RefereeDto referee);
	public abstract void deleteReferee(Integer id);
}
