package com.sam.webapi.service;

import com.google.common.collect.Iterables;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.*;
import com.sam.webapi.model.Player;
import com.sam.webapi.model.Team;
import com.sam.webapi.model.TeamPlayerReport;
import com.sam.webapi.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class ReportServiceImpl implements ReportService {

	private static final int MAIN_PLAYERS_IN_LINEUP = 11;
	private static final int RESERVE_PLAYERS_IN_LINEUP = 7;

	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;
	private final ReportRepository reportRepository;
	private final TeamPlayerReportRepository teamPlayerReportRepository;

	@Autowired
	public ReportServiceImpl(TeamRepository teamRepository,
							 PlayerRepository playerRepository,
							 ReportRepository reportRepository,
							 TeamPlayerReportRepository teamPlayerReportRepository) {
		this.reportRepository = reportRepository;
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
		this.teamPlayerReportRepository = teamPlayerReportRepository;
	}

	@Override
	@Transactional
	public void addLineupToReport(Integer id, String userEmail, LineupDto lineupDto) {

		var report = reportRepository.findById(id);

		if (report.isEmpty())
			throw new BadRequestException();

		var tournamentTeamMatch = report.get().getMatchByMatchId().getTournamentTeamMatchesById();
		var teamId = tournamentTeamMatch.getTeamId();
		var otherTeamId = tournamentTeamMatch.getOtherTeamId();
		var teamEmail = teamRepository.findById(teamId).get()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		var otherTeamEmail = teamRepository.findById(otherTeamId).get()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();

		if (!(userEmail.equals(teamEmail) && lineupDto.getTeamId() == teamId) &&
			!(userEmail.equals(otherTeamEmail) && lineupDto.getTeamId() == otherTeamId))
			throw new UnauthorizedException();

		if (lineupDto.getMainPlayerIds().size() != MAIN_PLAYERS_IN_LINEUP ||
			lineupDto.getReservePlayerIds().size() != RESERVE_PLAYERS_IN_LINEUP)
			throw  new BadRequestException();

		for (var playerId : lineupDto.getMainPlayerIds()) {
			if (playerRepository.findById(playerId).isEmpty())
				throw new BadRequestException();
		}

		for (var playerId : lineupDto.getReservePlayerIds()) {
			if (playerRepository.findById(playerId).isEmpty())
				throw new BadRequestException();
		}

		for (var playerId : lineupDto.getMainPlayerIds()) {
			teamPlayerReportRepository.save(new TeamPlayerReport(
					id,
					lineupDto.getTeamId(),
					playerId,
					"N"
			));
		}
		for (var playerId : lineupDto.getReservePlayerIds()) {
			teamPlayerReportRepository.save(new TeamPlayerReport(
					id,
					lineupDto.getTeamId(),
					playerId,
					"Y"
			));
		}
	}
}
