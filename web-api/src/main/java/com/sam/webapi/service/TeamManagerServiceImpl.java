package com.sam.webapi.service;

import com.sam.webapi.dataaccess.RegisteredUserRepository;
import com.sam.webapi.dataaccess.TeamManagerRepository;
import com.sam.webapi.dataaccess.TeamRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.TeamManagerDto;
import com.sam.webapi.model.RegisteredUser;
import com.sam.webapi.model.Team;
import com.sam.webapi.model.TeamManager;
import com.sam.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class TeamManagerServiceImpl implements TeamManagerService {

	private final TeamManagerRepository teamManagerRepository;
	private final RegisteredUserRepository registeredUserRepository;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;

	@Autowired
	public TeamManagerServiceImpl(TeamManagerRepository teamManagerRepository,
								  RegisteredUserRepository registeredUserRepository,
								  UserRepository userRepository,
								  TeamRepository teamRepository) {
		this.teamManagerRepository = teamManagerRepository;
		this.registeredUserRepository = registeredUserRepository;
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
	}

	@Override
	@Transactional
	public Iterable<TeamManagerDto> getTeamManagers(String adminEmail) {
		isAuthorizedUser(adminEmail);

		var teamManagers = teamManagerRepository.findAll();

		var teamManagersDto = new ArrayList<TeamManagerDto>();
		teamManagers.forEach(teamManager -> teamManagersDto.add(convertEntityToDto(teamManager)));

		return teamManagersDto;
	}

	@Override
	@Transactional
	public TeamManagerDto getTeamManager(Integer id, String adminEmail) {
		isAuthorizedUser(adminEmail);

		var teamManager = teamManagerRepository.findById(id);

		if (teamManager.isEmpty())
			throw new TeamManagerNotFoundException();

		return convertEntityToDto(teamManager.get());
	}

	@Override
	@Transactional
	public void createTeamManager(String adminEmail, TeamManagerDto teamManagerDto) {
		isAuthorizedUser(adminEmail);

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

		var adminUser = userRepository.findByEmailAndActive(adminEmail, "Y");
		var registeredUser = new RegisteredUser(
				user.getId(),
				adminUser.getId(),
				teamManagerDto.getPhone(),
				teamManagerDto.getAddress()
		);

		var teamManager = new TeamManager(
				user.getId()
		);

		var teamId = teamRepository.getMaxId();
		var team = new Team(
				++teamId,
				teamManager.getId(),
				teamManagerDto.getTeamName(),
				null,
				null,
				null
		);

		userRepository.save(user);
		registeredUserRepository.save(registeredUser);
		teamManagerRepository.save(teamManager);
		teamRepository.save(team);
	}

	@Override
	@Transactional
	public void updateTeamManager(Integer id, TeamManagerDto teamManagerDto, String adminEmail) {
		isAuthorizedUser(adminEmail);

		var user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException();

		var registeredUser = registeredUserRepository.findById(id);
		if (registeredUser.isEmpty())
			throw new RegisteredUserNotFoundException();

		var teamManager = teamManagerRepository.findById(id);
		if (teamManager.isEmpty())
			throw new TeamManagerNotFoundException();

		var team = teamRepository.findByTeamManagerId(id);
		if (team.isEmpty())
			throw new TeamNotFoundException();

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
		if (teamManagerDto.getTeamName() != null && !teamManagerDto.getTeamName().isEmpty())
			team.get().setName(teamManagerDto.getTeamName());

		userRepository.save(user.get());
		registeredUserRepository.save(registeredUser.get());
		teamManagerRepository.save(teamManager.get());
		teamRepository.save(team.get());
	}

	@Override
	@Transactional
	public void deleteTeamManager(Integer id, String adminEmail) {
		isAuthorizedUser(adminEmail);
		if (!teamManagerRepository.existsById(id))
			throw new TeamManagerNotFoundException();

		if (!registeredUserRepository.existsById(id))
			throw new RegisteredUserNotFoundException();

		if (!userRepository.existsById(id))
			throw new UserNotFoundException();

		userRepository.deactivateUserById(id);
	}

	private void isAuthorizedUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
			!user.getRole().equals("Admin"))
			throw new UnauthorizedException();
	}

	private TeamManagerDto convertEntityToDto(TeamManager teamManager) {
		var team = teamManager.getTeamById();
		var registeredUser = teamManager.getRegisteredUserById();
		var user =  registeredUser.getUserById();
		return new TeamManagerDto(
				user.getId(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				null,
				user.getActive(),
				registeredUser.getPhone(),
				registeredUser.getAddress(),
				team.getName()
		);
	}
}
