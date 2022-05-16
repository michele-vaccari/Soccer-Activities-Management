package com.sam.webapi.service;

import com.sam.webapi.dto.MatchDto;

public interface MatchService {
	void updateMatch(Integer id, MatchDto match, String adminEmail);
}
