package com.sam.webapi.service;

import com.sam.webapi.dto.TeamManagerDto;

public interface TeamManagerService {
	Iterable<TeamManagerDto> getTeamManagers();
	TeamManagerDto getTeamManager(Integer id);
	void createTeamManager(String adminEmail, TeamManagerDto teamManager);
	void updateTeamManager(Integer id, TeamManagerDto teamManager);
	void deleteTeamManager(Integer id);
}
