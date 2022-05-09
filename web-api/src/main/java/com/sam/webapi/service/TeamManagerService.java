package com.sam.webapi.service;

import com.sam.webapi.dto.TeamManagerDto;

public interface TeamManagerService {
	Iterable<TeamManagerDto> getTeamManagers(String adminEmail);
	TeamManagerDto getTeamManager(Integer id, String adminEmail);
	void createTeamManager(String adminEmail, TeamManagerDto teamManager);
	void updateTeamManager(Integer id, TeamManagerDto teamManager, String adminEmail);
	void deleteTeamManager(Integer id, String adminEmail);
}
