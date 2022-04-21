package com.sam.webapi.service;

import com.sam.webapi.dataaccess.PlayerRepository;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {

	private static final int MAX_PLAYERS_IN_TEAM = 36;
	private final PlayerRepository playerRepository;

	@Autowired
	public PlayerServiceImpl(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	@Transactional
	public PlayerDto getPlayer(Integer id) {
		var player = playerRepository.findById(id);
		if (player.isEmpty())
			throw new PlayerNotFoundException();

		return convertEntityToDto(player.get());
	}

	@Override
	@Transactional
	public void updatePlayer(Integer id, String userEmail, PlayerDto playerDto) {
		var player = playerRepository.findById(id);
		if (player.isEmpty())
			throw new PlayerNotFoundException();

		var teamManagerEmail = player.get()
				.getTeamByTeamId()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		if (!teamManagerEmail.equals(userEmail))
			throw new UnauthorizedException();

		if (playerDto.getJerseyNumber() != null)
			player.get().setJerseyNumber(playerDto.getJerseyNumber());
		if (playerDto.getRole() != null && !playerDto.getRole().isEmpty())
			player.get().setRole(playerDto.getRole());
		if (playerDto.getName() != null && !playerDto.getName().isEmpty())
			player.get().setName(playerDto.getName());
		if (playerDto.getSurname() != null && !playerDto.getSurname().isEmpty())
			player.get().setSurname(playerDto.getSurname());
		if (playerDto.getBirthDate() != null && !playerDto.getBirthDate().isEmpty())
			player.get().setBirthDate(playerDto.getBirthDate());
		if (playerDto.getCitizenship() != null && !playerDto.getCitizenship().isEmpty())
			player.get().setCitizenship(playerDto.getCitizenship());
		if (playerDto.getDescription() != null && !playerDto.getDescription().isEmpty())
			player.get().setDescription(playerDto.getDescription());
		if (playerDto.getGoal() != null)
			player.get().setGoal(playerDto.getGoal());
		if (playerDto.getAdmonitions() != null)
			player.get().setAdmonitions(playerDto.getAdmonitions());
		if (playerDto.getEjections() != null)
			player.get().setEjections(playerDto.getEjections());

		playerRepository.save(player.get());
	}

	@Override
	@Transactional
	public void deletePlayer(Integer id, String userEmail) {
		var player = playerRepository.findById(id);
		if (player.isEmpty())
			throw new PlayerNotFoundException();

		var teamManagerEmail = player.get()
				.getTeamByTeamId()
				.getTeamManagerByTeamManagerId()
				.getRegisteredUserById()
				.getUserById()
				.getEmail();
		if (!teamManagerEmail.equals(userEmail))
			throw new UnauthorizedException();

		player.get().setActive("N");
		playerRepository.save(player.get());
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
