package com.sam.webapi.service;

import com.sam.webapi.dto.TeamManagerDto;

import java.util.Optional;

public interface TeamManagerService {
	Iterable<TeamManagerDto> getTeamManagers();
	Optional<TeamManagerDto> getTeamManager(Integer id);
	void createTeamManager(String adminEmail, TeamManagerDto teamManager);
	void updateTeamManager(Integer id, TeamManagerDto teamManager);
	void deleteTeamManager(Integer id);
}
