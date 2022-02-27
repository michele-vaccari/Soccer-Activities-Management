package com.sam.webapi.service;

import com.sam.webapi.dto.TeamManagerDto;

import java.util.Optional;

public interface TeamManagerService {
	public abstract Iterable<TeamManagerDto> getTeamManagers();
	public abstract Optional<TeamManagerDto> getTeamManager(Integer id);
	public abstract void createTeamManager(String adminEmail, TeamManagerDto teamManager);
	public abstract void updateTeamManager(Integer id, TeamManagerDto teamManager);
	public abstract void deleteTeamManager(Integer id);
}
