package com.sam.webapi.service;

import com.google.common.collect.Iterables;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.*;
import com.sam.webapi.model.*;
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
	private final PlayerReportRepository playerReportRepository;
	private final RankingRepository rankingRepository;
	private final TournamentTeamRepository tournamentTeamRepository;
	private final TournamentTeamMatchRepository tournamentTeamMatchRepository;
	private final MatchRepository matchRepository;

	@Autowired
	public ReportServiceImpl(TeamRepository teamRepository,
							 PlayerRepository playerRepository,
							 ReportRepository reportRepository,
							 TeamPlayerReportRepository teamPlayerReportRepository,
							 PlayerReportRepository playerReportRepository,
							 RankingRepository rankingRepository,
							 TournamentTeamRepository tournamentTeamRepository,
							 TournamentTeamMatchRepository tournamentTeamMatchRepository,
							 MatchRepository matchRepository) {
		this.reportRepository = reportRepository;
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
		this.teamPlayerReportRepository = teamPlayerReportRepository;
		this.playerReportRepository = playerReportRepository;
		this.rankingRepository = rankingRepository;
		this.tournamentTeamRepository = tournamentTeamRepository;
		this.tournamentTeamMatchRepository = tournamentTeamMatchRepository;
		this.matchRepository = matchRepository;
	}

	@Override
	@Transactional
	public Iterable<ShortReportDto> getReports(String refereeEmail) {

		var reports = reportRepository.findAll();

		var shortReportsDto = new ArrayList<ShortReportDto>();
		for (var report : reports) {
			var isCompiled = report.getMatchStartTime() != null && !report.getMatchStartTime().isEmpty() &&
					report.getMatchEndTime() != null && !report.getMatchEndTime().isEmpty() &&
					report.getResult() != null && !report.getResult().isEmpty();

			var tournamentTeamMatch = report.getMatchByMatchId().getTournamentTeamMatchesById();
			var teamId = tournamentTeamMatch.getTeamId();
			var otherTeamId = tournamentTeamMatch.getOtherTeamId();

			var teamName = teamRepository.getById(teamId).get().getName();
			var otherTeamName = teamRepository.getById(otherTeamId).get().getName();

			var match = report.getMatchByMatchId();
			var matchPlace = match.getPlace();
			var matchDate = match.getDate();
			var matchResult = report.getResult();

			var tournamentName = match.getTournamentTeamMatchesById().getTournamentByTournamentId().getName();

			var referee = report.getRefereeByRefereeId();
			var userReferee = referee.getRegisteredUserById().getUserById();
			var refereeName = userReferee.getName();
			var refereeSurname = userReferee.getSurname();

			shortReportsDto.add(
					new ShortReportDto(
							report.getId(),
							tournamentName,
							teamName,
							otherTeamName,
							matchDate,
							refereeName,
							refereeSurname,
							matchPlace,
							matchResult,
							isCompiled
					)
			);
		}

		return shortReportsDto;
	}

	@Override
	@Transactional
	public ReportDto getReport(Integer id) {
		var report = reportRepository.findById(id);
		if (report.isEmpty())
			throw new ReportNotFoundException();

		var isCompiled = report.get().getMatchStartTime() != null && !report.get().getMatchStartTime().isEmpty() &&
				report.get().getMatchEndTime() != null && !report.get().getMatchEndTime().isEmpty() &&
				report.get().getResult() != null && !report.get().getResult().isEmpty();

		if (!isCompiled)
			throw new ReportNotFoundException();

		// set result
		var reportDto = new ReportDto();
		reportDto.setId(report.get().getId());
		var referee = report.get().getRefereeByRefereeId();
		reportDto.setRefereeId(referee.getId());
		var userReferee = referee.getRegisteredUserById().getUserById();
		reportDto.setRefereeName(userReferee.getName());
		reportDto.setRefereeSurname(userReferee.getSurname());

		var tournamentTeamMatch = report.get().getMatchByMatchId().getTournamentTeamMatchesById();
		var teamId = tournamentTeamMatch.getTeamId();
		var otherTeamId = tournamentTeamMatch.getOtherTeamId();

		var teamName = teamRepository.getById(teamId).get().getName();
		reportDto.setTeamName(teamName);
		var otherTeamName = teamRepository.getById(otherTeamId).get().getName();
		reportDto.setOtherTeamName(otherTeamName);

		var teamPlayersReports = report.get().getTeamPlayerReportsById();
		var mainTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var reserveTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var mainOtherTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var reserveOtherTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();

		var playersReport = report.get().getPlayerReportsById();

		for (var teamPlayerReport : teamPlayersReports) {
			var playerId = teamPlayerReport.getPlayerId();

			var player = playerRepository.findById(playerId).get();

			var goal = 0;
			var admonitions = 0;
			var ejection = false;

			for (var playerReport : playersReport) {
				if (playerReport.getPlayerId() == playerId) {
					goal = playerReport.getGoal();
					admonitions = playerReport.getAdmonitions();
					ejection = playerReport.getEjection().equals("Y");
				}
			}

			var playerReportDto = new PlayerReportDto(
					player.getId(),
					player.getJerseyNumber(),
					player.getName(),
					player.getSurname(),
					goal,
					admonitions,
					ejection
			);

			if (teamPlayerReport.getTeamId() == teamId) {
				if (teamPlayerReport.getReserve() == "Y")
					mainTeamPlayerReportsDto.add(playerReportDto);
				else
					reserveTeamPlayerReportsDto.add(playerReportDto);
			}
			else {
				if (teamPlayerReport.getReserve() == "Y")
					mainOtherTeamPlayerReportsDto.add(playerReportDto);
				else
					reserveOtherTeamPlayerReportsDto.add(playerReportDto);
			}
		}

		reportDto.setMainTeamPlayerReportsDto(mainTeamPlayerReportsDto);
		reportDto.setReserveTeamPlayerReportsDto(reserveTeamPlayerReportsDto);
		reportDto.setMainOtherTeamPlayerReportsDto(mainOtherTeamPlayerReportsDto);
		reportDto.setReserveOtherTeamPlayerReportsDto(reserveOtherTeamPlayerReportsDto);

		var match = report.get().getMatchByMatchId();
		reportDto.setMatchPlace(match.getPlace());
		reportDto.setMatchDate(match.getDate());
		reportDto.setMatchStartTime(report.get().getMatchStartTime());
		reportDto.setMatchEndTime(report.get().getMatchEndTime());
		reportDto.setResult(report.get().getResult());

		var tournamentName = match.getTournamentTeamMatchesById().getTournamentByTournamentId().getName();
		reportDto.setTournamentName(tournamentName);

		return reportDto;
	}

	@Override
	@Transactional
	public ReportDto getLineups(Integer id, String refereeEmail) {
		var report = reportRepository.findById(id);
		if (report.isEmpty())
			throw new ReportNotFoundException();

		var reportRefereeEmail = report.get()
				.getRefereeByRefereeId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();

		if (!reportRefereeEmail.equals(refereeEmail))
			throw new UnauthorizedException();

		var isCompiled = report.get().getMatchStartTime() != null && !report.get().getMatchStartTime().isEmpty() &&
				report.get().getMatchEndTime() != null && !report.get().getMatchEndTime().isEmpty() &&
				report.get().getResult() != null && !report.get().getResult().isEmpty();

		if (isCompiled)
			throw new BadRequestException();

		if (teamPlayerReportRepository.findDistinctTeamIdsByReportId(report.get().getId()).size() != 2)
			throw new BadRequestException();

		// set result
		var reportDto = new ReportDto();
		reportDto.setId(report.get().getId());

		var tournamentTeamMatch = report.get().getMatchByMatchId().getTournamentTeamMatchesById();
		var teamId = tournamentTeamMatch.getTeamId();
		var otherTeamId = tournamentTeamMatch.getOtherTeamId();

		var teamName = teamRepository.getById(teamId).get().getName();
		reportDto.setTeamName(teamName);
		var otherTeamName = teamRepository.getById(otherTeamId).get().getName();
		reportDto.setOtherTeamName(otherTeamName);

		var teamPlayersReports = report.get().getTeamPlayerReportsById();
		var mainTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var reserveTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var mainOtherTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();
		var reserveOtherTeamPlayerReportsDto = new ArrayList<PlayerReportDto>();

		for (var teamPlayerReport : teamPlayersReports) {
			var playerId = teamPlayerReport.getPlayerId();

			var player = playerRepository.findById(playerId).get();

			var playerReportDto = new PlayerReportDto(
					player.getId(),
					player.getJerseyNumber(),
					player.getName(),
					player.getSurname()
			);

			if (teamPlayerReport.getTeamId() == teamId) {
				if (teamPlayerReport.getReserve() == "Y")
					mainTeamPlayerReportsDto.add(playerReportDto);
				else
					reserveTeamPlayerReportsDto.add(playerReportDto);
			}
			else {
				if (teamPlayerReport.getReserve() == "Y")
					mainOtherTeamPlayerReportsDto.add(playerReportDto);
				else
					reserveOtherTeamPlayerReportsDto.add(playerReportDto);
			}
		}

		reportDto.setMainTeamPlayerReportsDto(mainTeamPlayerReportsDto);
		reportDto.setReserveTeamPlayerReportsDto(reserveTeamPlayerReportsDto);
		reportDto.setMainOtherTeamPlayerReportsDto(mainOtherTeamPlayerReportsDto);
		reportDto.setReserveOtherTeamPlayerReportsDto(reserveOtherTeamPlayerReportsDto);

		var match = report.get().getMatchByMatchId();
		reportDto.setMatchPlace(match.getPlace());
		reportDto.setMatchDate(match.getDate());

		var tournamentName = match.getTournamentTeamMatchesById().getTournamentByTournamentId().getName();
		reportDto.setTournamentName(tournamentName);

		return reportDto;
	}

	@Override
	@Transactional
	public void compileReport(Integer id, String refereeEmail, ReportDto reportDto) {

		var report = reportRepository.findById(id);
		if (report.isEmpty())
			throw new ReportNotFoundException();

		var reportRefereEmail = report.get()
				.getRefereeByRefereeId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		if (!reportRefereEmail.equals(refereeEmail))
			throw new UnauthorizedException();

		var playerIds = teamPlayerReportRepository.findPlayerIdsByReportId(id);

		var playersReportDto = Iterables.concat(
				reportDto.getMainTeamPlayerReportsDto(),
				reportDto.getReserveTeamPlayerReportsDto(),
				reportDto.getMainOtherTeamPlayerReportsDto(),
				reportDto.getReserveTeamPlayerReportsDto()
				);
		for (var playerReport : playersReportDto) {
			if (!playerIds.contains(playerReport.getId()))
				throw new BadRequestException();
		}

		for (var playerReport : playersReportDto) {
			playerReportRepository.save(new PlayerReport(
					report.get().getId(),
					playerReport.getId(),
					playerReport.getGoal(),
					playerReport.getAdmonitions(),
					playerReport.isEjection() ? "Y" : "N"
					));
		}

		report.get().setMatchStartTime(reportDto.getMatchStartTime());
		report.get().setMatchEndTime(reportDto.getMatchEndTime());

		var tournamentTeamMatch = report.get().getMatchByMatchId().getTournamentTeamMatchesById();
		var teamId = tournamentTeamMatch.getTeamId();
		var otherTeamId = tournamentTeamMatch.getOtherTeamId();
		var playersOfTeam = teamPlayerReportRepository.findPlayerIdsByReportIdAndTeamId(report.get().getId(), teamId);
		var goalOfTeam = 0;
		var goalOfOtherTeam = 0;
		for (var playerReport : playersReportDto) {
			if (playersOfTeam.contains(playerReport))
				goalOfTeam += playerReport.getGoal();
			else
				goalOfOtherTeam += playerReport.getGoal();
		}
		goalOfTeam += reportDto.getAutogoalOtherTeam();
		goalOfOtherTeam += reportDto.getAutogoalTeam();
		var result = goalOfTeam + " - " + goalOfOtherTeam;
		report.get().setResult(result);

		// update ranking
		var match = report.get().getMatchByMatchId();
		var tournamentId = match.getTournamentTeamMatchesById().getTournamentId();

		var teamRanking = rankingRepository.findById(new RankingPK(tournamentId, teamId)).get();
		var otherTeamRanking = rankingRepository.findById(new RankingPK(tournamentId, otherTeamId)).get();

		if (goalOfTeam == goalOfTeam) {
			teamRanking.setScore(teamRanking.getScore() + 1);
			teamRanking.setTiedMatches(teamRanking.getTiedMatches() + 1);

			otherTeamRanking.setScore(teamRanking.getScore() + 1);
			otherTeamRanking.setTiedMatches(teamRanking.getTiedMatches() + 1);
		}
		else if (goalOfTeam > goalOfOtherTeam) {
			teamRanking.setScore(teamRanking.getScore() + 3);
			teamRanking.setWonMatches(teamRanking.getWonMatches() + 1);

			otherTeamRanking.setLostMatches(teamRanking.getLostMatches() + 1);
		}
		else {
			otherTeamRanking.setScore(teamRanking.getScore() + 3);
			otherTeamRanking.setWonMatches(teamRanking.getWonMatches() + 1);

			teamRanking.setLostMatches(teamRanking.getLostMatches() + 1);
		}

		teamRanking.setPlayedMatches(teamRanking.getPlayedMatches() + 1);
		teamRanking.setGoalsMade(teamRanking.getGoalsMade() + goalOfTeam);
		teamRanking.setGoalsSuffered(teamRanking.getGoalsMade() + goalOfOtherTeam);

		otherTeamRanking.setPlayedMatches(teamRanking.getPlayedMatches() + 1);
		otherTeamRanking.setGoalsMade(teamRanking.getGoalsMade() + goalOfOtherTeam);
		otherTeamRanking.setGoalsSuffered(teamRanking.getGoalsMade() + goalOfTeam);

		// create new single elimination match or update the existing one
		var winnerTeamId = goalOfTeam > goalOfOtherTeam ? teamId : otherTeamId;

		if (match.getType().equals("SingleElimination")) {
			var teamIds = tournamentTeamRepository.findTeamIdsByTournamentId(tournamentId);
			var numberOfteams = teamIds.size();
			var maxNumberOfMatch = numberOfteams - 1;

			var currentMatchNumber = Integer.parseInt(match.getTournamentTeamMatchesById().getMatchName());

			if (currentMatchNumber == maxNumberOfMatch)
				return;

			var nextMatchNumber = (numberOfteams / 2) + 1 + (currentMatchNumber / 2);

			if (!tournamentTeamMatchRepository.existsByTournamentIdAndMatchName(tournamentId, Integer.toString(nextMatchNumber))) {

				var matchId = matchRepository.getMaxId();
				var newMatch = new Match(
						matchId == null ? 1 : ++matchId,
						"E",
						"",
						"",
						""
				);
				matchRepository.save(newMatch);

				var newTournamentTeamMatch = new TournamentTeamMatch(
						match.getId(),
						winnerTeamId,
						winnerTeamId,
						tournamentId,
						Integer.toString(nextMatchNumber)
				);
				tournamentTeamMatchRepository.save(newTournamentTeamMatch);
			}

			var newTournamentTeamMatch = tournamentTeamMatchRepository.findByTournamentIdAndMatchName(tournamentId, Integer.toString(nextMatchNumber));
			if (currentMatchNumber % 2 == 0)
				newTournamentTeamMatch.setOtherTeamId(winnerTeamId);
			else
				newTournamentTeamMatch.setTeamId(winnerTeamId);
		}
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
