package com.sam.webapi.service;

import com.google.common.collect.Iterables;
import com.sam.webapi.dataaccess.PlayerRepository;
import com.sam.webapi.dataaccess.TeamRepository;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.TeamDto;
import com.sam.webapi.model.Player;
import com.sam.webapi.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class TeamServiceImpl implements TeamService {

	private static final int MAX_PLAYERS_IN_TEAM = 36;
	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;

	@Autowired
	public TeamServiceImpl(TeamRepository teamRepository,
						   PlayerRepository playerRepository) {
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
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
