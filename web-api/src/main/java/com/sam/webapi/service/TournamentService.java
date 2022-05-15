package com.sam.webapi.service;

import com.sam.webapi.dto.ShortTournamentDto;
import com.sam.webapi.dto.TournamentDto;

public interface TournamentService {
	Iterable<ShortTournamentDto> getTournaments();
	Iterable<ShortTournamentDto> getTournaments(Integer teamId);
	TournamentDto getTournament(Integer id);
	void createTournament(String adminEmail, TournamentDto tournament);
}
