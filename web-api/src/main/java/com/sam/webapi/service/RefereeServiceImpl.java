package com.sam.webapi.service;

import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.RefereeDto;
import com.sam.webapi.dto.ShortReportDto;
import com.sam.webapi.model.Referee;
import com.sam.webapi.model.RegisteredUser;
import com.sam.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class RefereeServiceImpl implements RefereeService {

	private final RefereeRepository refereeRepository;
	private final RegisteredUserRepository registeredUserRepository;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final TeamPlayerReportRepository teamPlayerReportRepository;

	@Autowired
	public RefereeServiceImpl(RefereeRepository refereeRepository,
							  RegisteredUserRepository registeredUserRepository,
							  UserRepository userRepository,
							  TeamRepository teamRepository,
							  TeamPlayerReportRepository teamPlayerReportRepository) {
		this.refereeRepository = refereeRepository;
		this.registeredUserRepository = registeredUserRepository;
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this.teamPlayerReportRepository = teamPlayerReportRepository;
	}

	@Override
	@Transactional
	public Iterable<RefereeDto> getReferees(String adminEmail) {
		isAdminUser(adminEmail);

		var referees = refereeRepository.findAll();

		var refereesDto = new ArrayList<RefereeDto>();
		referees.forEach(referee -> refereesDto.add(convertEntityToDto(referee)));

		return refereesDto;
	}

	@Override
	@Transactional
	public RefereeDto getReferee(Integer id, String adminEmail) {
		isAdminUser(adminEmail);

		var referee = refereeRepository.findById(id);

		if (referee.isEmpty())
			throw new RefereeNotFoundException();

		return convertEntityToDto(referee.get());
	}

	@Override
	@Transactional
	public void createReferee(String adminEmail, RefereeDto refereeDto) {
		isAdminUser(adminEmail);

		var adminUser = userRepository.findByEmailAndActive(adminEmail, "Y");

		if (userRepository.existsByEmail(refereeDto.getEmail()))
			throw new SingleEmailConstraintException();

		var userId = userRepository.getMaxId();

		var user = new User(
				++userId,
				"Referee",
				refereeDto.getName(),
				refereeDto.getSurname(),
				refereeDto.getEmail(),
				refereeDto.getPassword(),
				"Y"
		);

		var registeredUser = new RegisteredUser(
				user.getId(),
				adminUser.getId(),
				refereeDto.getPhone(),
				refereeDto.getAddress()
		);

		var referee = new Referee(
				user.getId(),
				refereeDto.getBirthDate(),
				refereeDto.getCitizenship(),
				refereeDto.getResume()
		);

		userRepository.save(user);
		registeredUserRepository.save(registeredUser);
		refereeRepository.save(referee);
	}

	@Override
	@Transactional
	public Iterable<ShortReportDto> getReportsOfReferee(Integer id, String refereeEmail) {
		isRefereeUser(refereeEmail);

		var user = userRepository.findByEmailAndActive(refereeEmail, "Y");
		if (user.getId() != id)
			throw new BadRequestException();

		var reports = user.getRegisteredUserById()
				.getRefereeById()
				.getReportsById();

		var reportsDto = new ArrayList<ShortReportDto>();
		for (var report : reports ) {
			var match = report.getMatchByMatchId();
			var tournamentTeamMatch = match.getTournamentTeamMatchesById();
			var tournamentName = tournamentTeamMatch.getTournamentByTournamentId().getName();

			var teamIds = teamPlayerReportRepository.findDistinctTeamIdsByReportId(report.getId());
			var teamLineupSubmitted = teamIds.contains(tournamentTeamMatch.getTeamId());
			var otherTeamLineupSubmitted = teamIds.contains(tournamentTeamMatch.getOtherTeamId());

			if (!teamLineupSubmitted ||
				!otherTeamLineupSubmitted)
				continue;

			var teamId = tournamentTeamMatch.getTeamId();
			var otherTeamId = tournamentTeamMatch.getOtherTeamId();
			var teamName = teamRepository.getById(teamId).get().getName();
			var otherTeamName = teamRepository.getById(otherTeamId).get().getName();

			var compiled = report.getResult() != null && !report.getResult().isEmpty() &&
						   report.getMatchStartTime() != null && !report.getMatchStartTime().isEmpty() &&
						   report.getMatchEndTime() != null && !report.getMatchEndTime().isEmpty();

			reportsDto.add(new ShortReportDto(
					report.getId(),
					tournamentName,
					teamName,
					otherTeamName,
					match.getDate(),
					report.getResult(),
					compiled
			));
		}
		return reportsDto;
	}

	@Override
	@Transactional
	public void updateReferee(Integer id, RefereeDto refereeDto, String adminEmail) {
		isAdminUser(adminEmail);

		var user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException();

		var registeredUser = registeredUserRepository.findById(id);
		if (registeredUser.isEmpty())
			throw new RegisteredUserNotFoundException();

		var referee = refereeRepository.findById(id);
		if (referee.isEmpty())
			throw new RefereeNotFoundException();

		if (userRepository.existsByEmailAndIdNot(refereeDto.getEmail(), id))
			throw new SingleEmailConstraintException();

		if (refereeDto.getName() != null && !refereeDto.getName().isEmpty())
			user.get().setName(refereeDto.getName());
		if (refereeDto.getSurname() != null && !refereeDto.getSurname().isEmpty())
			user.get().setSurname(refereeDto.getSurname());
		if (refereeDto.getEmail() != null && !refereeDto.getEmail().isEmpty())
			user.get().setEmail(refereeDto.getEmail());
		if (refereeDto.getPassword() != null && !refereeDto.getPassword().isEmpty())
			user.get().setPassword(refereeDto.getPassword());
		if (refereeDto.getActive() != null && !refereeDto.getActive().isEmpty())
			user.get().setActive(refereeDto.getActive());
		if (refereeDto.getPhone() != null && !refereeDto.getPhone().isEmpty())
			registeredUser.get().setPhone(refereeDto.getPhone());
		if (refereeDto.getAddress() != null && !refereeDto.getAddress().isEmpty())
			registeredUser.get().setAddress(refereeDto.getAddress());
		if (refereeDto.getBirthDate() != null && !refereeDto.getBirthDate().isEmpty())
			referee.get().setBirthDate(refereeDto.getBirthDate());
		if (refereeDto.getCitizenship() != null && !refereeDto.getCitizenship().isEmpty())
			referee.get().setCitizenship(refereeDto.getCitizenship());
		if (refereeDto.getResume() != null && !refereeDto.getResume().isEmpty())
			referee.get().setResume(refereeDto.getResume());

		userRepository.save(user.get());
		registeredUserRepository.save(registeredUser.get());
		refereeRepository.save(referee.get());
	}

	@Override
	@Transactional
	public void deleteReferee(Integer id, String adminEmail) {
		isAdminUser(adminEmail);

		if (!refereeRepository.existsById(id))
			throw new RefereeNotFoundException();

		if (!registeredUserRepository.existsById(id))
			throw new RegisteredUserNotFoundException();

		if (!userRepository.existsById(id))
			throw new UserNotFoundException();

		userRepository.deactivateUserById(id);
	}

	private void isAdminUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
				!user.getRole().equals("Admin"))
			throw new UnauthorizedException();
	}

	private void isRefereeUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
				!user.getRole().equals("Referee"))
			throw new UnauthorizedException();
	}

	private RefereeDto convertEntityToDto(Referee referee) {
		var registeredUser = referee.getRegisteredUserById();
		var user =  registeredUser.getUserById();
		return new RefereeDto(
				user.getId(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				null,
				user.getActive(),
				registeredUser.getPhone(),
				registeredUser.getAddress(),
				referee.getBirthDate(),
				referee.getCitizenship(),
				referee.getResume()
		);
	}
}
