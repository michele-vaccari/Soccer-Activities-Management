package com.sam.webapi.service;

import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.TeamDto;

import java.util.Optional;

public interface TeamService {
	Iterable<TeamDto> getTeams();
	Optional<TeamDto> getTeam(Integer id);
	void updateTeam(Integer id, String userEmail, TeamDto teamDto);
	Iterable<PlayerDto> getPlayersOfTeam(Integer teamId);
	void createPlayerOfTeam(Integer id, String userEmail, PlayerDto playerDto);
}
