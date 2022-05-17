package com.sam.webapi.service;

import com.sam.webapi.dto.MatchDto;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.ShortTournamentDto;
import com.sam.webapi.dto.TeamDto;

public interface TeamService {
	Iterable<TeamDto> getTeams();
	TeamDto getTeam(Integer id);
	void updateTeam(Integer id, String userEmail, TeamDto teamDto);
	Iterable<ShortTournamentDto> getTournamentsOfTeam(Integer teamId);
	Iterable<MatchDto> getMatchesOfTeam(Integer teamId, String userEmail);
	Iterable<PlayerDto> getPlayersOfTeam(Integer teamId);
	void createPlayerOfTeam(Integer id, String userEmail, PlayerDto playerDto);
}
