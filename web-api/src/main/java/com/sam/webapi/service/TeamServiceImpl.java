package com.sam.webapi.service;

import com.google.common.collect.Iterables;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.MatchDto;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.ShortTournamentDto;
import com.sam.webapi.dto.TeamDto;
import com.sam.webapi.model.Player;
import com.sam.webapi.model.Team;
import com.sam.webapi.model.TeamPlayerReport;
import com.sam.webapi.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class TeamServiceImpl implements TeamService {

	private static final int MAX_PLAYERS_IN_TEAM = 36;
	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;
	private final TournamentTeamRepository tournamentTeamRepository;
	private final TournamentRepository tournamentRepository;
	private final TeamPlayerReportRepository teamPlayerReportRepository;

	@Autowired
	public TeamServiceImpl(TeamRepository teamRepository,
						   PlayerRepository playerRepository,
						   TournamentTeamRepository tournamentTeamRepository,
						   TournamentRepository tournamentRepository,
						   TeamPlayerReportRepository teamPlayerReportRepository) {
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
		this.tournamentTeamRepository = tournamentTeamRepository;
		this.tournamentRepository = tournamentRepository;
		this.teamPlayerReportRepository = teamPlayerReportRepository;
	}

	@Override
	@Transactional
	public Iterable<TeamDto> getTeams() {
		var teams = teamRepository.findAll();

		var teamsDto = new ArrayList<TeamDto>();
		teams.forEach(team -> teamsDto.add(convertEntityToDto(team)));

		return teamsDto;
	}

	@Override
	@Transactional
	public TeamDto getTeam(Integer id) {
		var team = teamRepository.findById(id);

		if (team.isEmpty())
			throw new TeamNotFoundException();

		return convertEntityToDto(team.get());
	}

	@Override
	@Transactional
	public void updateTeam(Integer id, String userEmail, TeamDto teamDto) {
		var team = teamRepository.findById(id);
		if (team.isEmpty())
			throw new TeamNotFoundException();

		var teamManagerEmail = team.get()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		if (!teamManagerEmail.equals(userEmail))
			throw new UnauthorizedException();

		if (teamDto.getName() != null && !teamDto.getName().isEmpty())
			team.get().setName(teamDto.getName());
		if (teamDto.getDescription() != null && !teamDto.getDescription().isEmpty())
			team.get().setDescription(teamDto.getDescription());
		if (teamDto.getHeadquarters() != null && !teamDto.getHeadquarters().isEmpty())
			team.get().setHeadquarters(teamDto.getHeadquarters());
		if (teamDto.getSponsorName() != null && !teamDto.getSponsorName().isEmpty())
			team.get().setSponsorName(teamDto.getSponsorName());

		teamRepository.save(team.get());
	}

	@Override
	@Transactional
	public Iterable<ShortTournamentDto> getTournamentsOfTeam(Integer teamId) {
		var team = teamRepository.findById(teamId);
		if (team.isEmpty())
			throw new TeamNotFoundException();

		var tournamentTeams = tournamentTeamRepository.findAllByTeamId(teamId);

		var shortTournamentsDto =  new ArrayList<ShortTournamentDto>();
		tournamentTeams.forEach(tournamentTeam -> shortTournamentsDto.add(convertEntityToShortDto(tournamentRepository.findById(tournamentTeam.getTournamentId()).get())));

		return shortTournamentsDto;
	}

	@Override
	@Transactional
	public Iterable<MatchDto> getMatchesOfTeam(Integer id, String userEmail) {
		var team = teamRepository.findById(id);
		if (team.isEmpty())
			throw new TeamNotFoundException();

		var teamManagerEmail = team.get()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		if (!teamManagerEmail.equals(userEmail))
			throw new UnauthorizedException();

		var tournamentTeams = tournamentTeamRepository.findAllByTeamId(id);

		var matchesDto =  new ArrayList<MatchDto>();
		for (var tournamentTeam : tournamentTeams) {
			var tournament = tournamentTeam.getTournamentByTournamentId();
			var tournamentTeamMatches = tournament.getTournamentTeamMatchesById();
			for (var tournamentTeamMatch : tournamentTeamMatches) {
				var teamId = tournamentTeamMatch.getTeamId();
				var otherTeamId = tournamentTeamMatch.getOtherTeamId();
				if (teamId == id || otherTeamId == id) {
					var teamName = teamRepository.getById(teamId).get().getName();
					var otherTeamName = teamRepository.getById(otherTeamId).get().getName();
					var match = tournamentTeamMatch.getMatchByMatchId();
					var report = match.getReportsById();

					boolean teamLineupSubmitted = false;
					boolean otherTeamLineupSubmitted = false;
					if (report != null) {
						var teamIds = teamPlayerReportRepository.findDistinctTeamIdsByReportId(report.getId());
						teamLineupSubmitted = teamIds.contains(tournamentTeamMatch.getTeamId());
						otherTeamLineupSubmitted = teamIds.contains(tournamentTeamMatch.getOtherTeamId());
					}

					matchesDto.add(new MatchDto(
							tournamentTeamMatch.getMatchId(),
							tournament.getName(),
							tournamentTeamMatch.getMatchName(),
							tournamentTeamMatch.getTeamId(),
							tournamentTeamMatch.getOtherTeamId(),
							teamName,
							otherTeamName,
							report == null ? null : report.getId(),
							teamLineupSubmitted,
							otherTeamLineupSubmitted
					));
				}
			}
		}

		return matchesDto;
	}

	@Override
	@Transactional
	public Iterable<PlayerDto> getPlayersOfTeam(Integer teamId) {
		var team = teamRepository.findById(teamId);
		if (team.isEmpty())
			throw new TeamNotFoundException();

		var players = team.get().getPlayersById();
		var playersDto = new ArrayList<PlayerDto>();
		players.forEach(player -> playersDto.add(convertEntityToDto(player)));

		return playersDto;
	}

	@Override
	@Transactional
	public void createPlayerOfTeam(Integer id, String userEmail, PlayerDto playerDto) {
		if(!playerDto.isValid())
			throw new BadRequestException();

		var team = teamRepository.findById(id);
		if (team.isEmpty())
			throw new TeamNotFoundException();

		var teamManagerEmail = team.get()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		var teamId = team.get().getId();
		if (!teamManagerEmail.equals(userEmail) ||
			teamId != playerDto.getTeamId())
			throw new UnauthorizedException();

		var playersInTeam = Iterables.size(playerRepository.findByTeamIdAndActive(id, "Y"));
		if (playersInTeam >= MAX_PLAYERS_IN_TEAM)
			throw new MaxPlayersInTeamException();

		var playerMaxId = playerRepository.getMaxId();
		var player = new Player(
				playerMaxId == null ? 1 : ++playerMaxId,
				playerDto.getTeamId(),
				"Y",
				playerDto.getJerseyNumber(),
				playerDto.getRole(),
				playerDto.getName(),
				playerDto.getSurname(),
				playerDto.getBirthDate(),
				playerDto.getCitizenship(),
				playerDto.getDescription(),
				0,
				0,
				0
		);

		playerRepository.save(player);
	}

	private TeamDto convertEntityToDto(Team team) {
		return new TeamDto(
				team.getId(),
				team.getName(),
				team.getDescription(),
				team.getHeadquarters(),
				team.getSponsorName()
		);
	}

	private ShortTournamentDto convertEntityToShortDto(Tournament tournament) {
		return new ShortTournamentDto(
				tournament.getId(),
				tournament.getName(),
				tournament.getType().equals("R") ? "RoundRobin" : "SingleElimination"
		);
	}

	private PlayerDto convertEntityToDto(Player player) {
		return new PlayerDto(
				player.getId(),
				player.getTeamId(),
				player.getActive(),
				player.getJerseyNumber(),
				player.getRole(),
				player.getName(),
				player.getSurname(),
				player.getBirthDate(),
				player.getCitizenship(),
				player.getDescription(),
				player.getGoal(),
				player.getAdmonitions(),
				player.getEjections()
		);
	}
}
