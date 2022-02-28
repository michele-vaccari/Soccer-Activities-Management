package com.sam.webapi.service;

import com.sam.webapi.dataaccess.RegisteredUserRepository;
import com.sam.webapi.dataaccess.TeamManagerRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.TeamManagerDto;
import com.sam.webapi.model.RegisteredUser;
import com.sam.webapi.model.TeamManager;
import com.sam.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeamManagerServiceImpl implements TeamManagerService {

	private final TeamManagerRepository teamManagerRepository;
	private final RegisteredUserRepository registeredUserRepository;
	private final UserRepository userRepository;

	@Autowired
	public TeamManagerServiceImpl(TeamManagerRepository teamManagerRepository,
								  RegisteredUserRepository registeredUserRepository,
								  UserRepository userRepository) {
		this.teamManagerRepository = teamManagerRepository;
		this.registeredUserRepository = registeredUserRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public Iterable<TeamManagerDto> getTeamManagers() {
		var teamManagers = teamManagerRepository.findAll();

		var teamManagersDto = new ArrayList<TeamManagerDto>();
		teamManagers.forEach(teamManager -> teamManagersDto.add(convertEntityToDto(teamManager)));

		return teamManagersDto;
	}

	@Override
	@Transactional
	public Optional<TeamManagerDto> getTeamManager(Integer id) {
		var teamManager = teamManagerRepository.findById(id);

		if (teamManager.isEmpty())
			return Optional.empty();
		else
			return Optional.of(convertEntityToDto(teamManager.get()));
	}

	@Override
	@Transactional
	public void createTeamManager(String adminEmail, TeamManagerDto teamManagerDto) {

		var adminUser = userRepository.findByEmailAndActive(adminEmail, "Y");
		if (adminUser == null)
			throw new AdminUserNotFoundException();

		if (userRepository.existsByEmail(teamManagerDto.getEmail()))
			throw new SingleEmailConstraintException();

		var userId = userRepository.getMaxId();

		var user = new User(
				++userId,
				"TeamManager",
				teamManagerDto.getName(),
				teamManagerDto.getSurname(),
				teamManagerDto.getEmail(),
				teamManagerDto.getPassword(),
				"Y"
		);

		var registeredUser = new RegisteredUser(
				user.getId(),
				adminUser.getId(),
				teamManagerDto.getPhone(),
				teamManagerDto.getAddress()
		);

		var teamManager = new TeamManager(
				user.getId()
		);

		userRepository.save(user);
		registeredUserRepository.save(registeredUser);
		teamManagerRepository.save(teamManager);
	}

	@Override
	@Transactional
	public void updateTeamManager(Integer id, TeamManagerDto teamManagerDto) {
		var user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException();

		var registeredUser = registeredUserRepository.findById(id);
		if (registeredUser.isEmpty())
			throw new RegisteredUserNotFoundException();

		var teamManager = teamManagerRepository.findById(id);
		if (teamManager.isEmpty())
			throw new TeamManagerNotFoundException();

		if (userRepository.existsByEmailAndIdNot(teamManagerDto.getEmail(), id))
			throw new SingleEmailConstraintException();

		if (teamManagerDto.getName() != null && !teamManagerDto.getName().isEmpty())
			user.get().setName(teamManagerDto.getName());
		if (teamManagerDto.getSurname() != null && !teamManagerDto.getSurname().isEmpty())
			user.get().setSurname(teamManagerDto.getSurname());
		if (teamManagerDto.getEmail() != null && !teamManagerDto.getEmail().isEmpty())
			user.get().setEmail(teamManagerDto.getEmail());
		if (teamManagerDto.getPassword() != null && !teamManagerDto.getPassword().isEmpty())
			user.get().setPassword(teamManagerDto.getPassword());
		if (teamManagerDto.getActive() != null && !teamManagerDto.getActive().isEmpty())
			user.get().setActive(teamManagerDto.getActive());
		if (teamManagerDto.getPhone() != null && !teamManagerDto.getPhone().isEmpty())
			registeredUser.get().setPhone(teamManagerDto.getPhone());
		if (teamManagerDto.getAddress() != null && !teamManagerDto.getAddress().isEmpty())
			registeredUser.get().setAddress(teamManagerDto.getAddress());

		userRepository.save(user.get());
		registeredUserRepository.save(registeredUser.get());
		teamManagerRepository.save(teamManager.get());
	}

	@Override
	@Transactional
	public void deleteTeamManager(Integer id) {
		if (!teamManagerRepository.existsById(id))
			throw new TeamManagerNotFoundException();

		if (!registeredUserRepository.existsById(id))
			throw new RegisteredUserNotFoundException();

		if (!userRepository.existsById(id))
			throw new UserNotFoundException();

		userRepository.deactivateUserById(id);
	}

	private TeamManagerDto convertEntityToDto(TeamManager teamManager) {
		var teamManagerDto = new TeamManagerDto();
		var registeredUser = teamManager.getRegisteredUserById();
		var user =  registeredUser.getUserById();
		teamManagerDto.setId(user.getId());
		teamManagerDto.setEmail(user.getEmail());
		teamManagerDto.setName(user.getName());
		teamManagerDto.setSurname(user.getSurname());
		teamManagerDto.setActive(user.getActive());
		teamManagerDto.setPhone(registeredUser.getPhone());
		teamManagerDto.setAddress(registeredUser.getAddress());

		return teamManagerDto;
	}
}