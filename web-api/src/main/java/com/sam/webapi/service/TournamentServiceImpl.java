package com.sam.webapi.service;

import com.google.common.math.IntMath;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.dto.MatchDto;
import com.sam.webapi.dto.ShortTournamentDto;
import com.sam.webapi.dto.TournamentDto;
import com.sam.webapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Console;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

	private static final int MAX_NUMBER_OF_TEAMS_FOR_SINGLE_ELIMINATION_TOURNAMENT = 16;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final TournamentRepository tournamentRepository;
	private final TournamentTeamRepository tournamentTeamRepository;
	private final RankingRepository rankingRepository;
	private final TournamentTeamMatchRepository tournamentTeamMatchRepository;
	private final MatchRepository matchRepository;

	@Autowired
	public TournamentServiceImpl(UserRepository userRepository,
								 TeamRepository teamRepository,
								 TournamentRepository tournamentRepository,
								 TournamentTeamRepository tournamentTeamRepository,
								 RankingRepository rankingRepository,
								 TournamentTeamMatchRepository tournamentTeamMatchRepository,
								 MatchRepository matchRepository) {
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this. tournamentRepository = tournamentRepository;
		this.tournamentTeamRepository = tournamentTeamRepository;
		this.rankingRepository = rankingRepository;
		this.tournamentTeamMatchRepository = tournamentTeamMatchRepository;
		this.matchRepository = matchRepository;
	}

	@Override
	@Transactional
	public Iterable<ShortTournamentDto> getTournaments() {
/*
		var admins = adminUserRepository.findAll();

		var adminsDto = new ArrayList<AdminDto>();
		admins.forEach(admin -> adminsDto.add(convertEntityToDto(admin)));

		return adminsDto;*/
		return new ArrayList<ShortTournamentDto>();
	}

	@Override
	@Transactional
	public TournamentDto getTournament(Integer id) {
/*
		var admin = adminUserRepository.findById(id);

		if (admin.isEmpty())
			throw new TournamentNotFoundException();

		return convertEntityToDto(admin.get());*/
		return new TournamentDto();
	}

	private AdminDto convertEntityToDto(AdminUser admin) {
		var user =  admin.getUserById();
		return new AdminDto(
				user.getId(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				null,
				user.getActive()
		);
	}

	@Override
	@Transactional
	public void createTournament(String adminEmail, TournamentDto tournamentDto) {
		isAuthorizedUser(adminEmail);
		for (var teamId: tournamentDto.getTeamIds()) {
			var team = teamRepository.getById(teamId);
			if (team.isEmpty())
				throw new TeamNotFoundException();
		}

		var admin = userRepository.findByEmailAndActive(adminEmail,"Y");

		var tournamentId = tournamentRepository.getMaxId();
		var tournament = new Tournament(
				tournamentId == null ? 1 : ++tournamentId,
				(tournamentDto.getType().equals("RoundRobin") ? "R" : "I"),
				tournamentDto.getName(),
				tournamentDto.getDescription(),
				admin.getId()
		);

		tournamentRepository.save(tournament);


		for (var teamId : tournamentDto.getTeamIds()) {
			var tournamentTeam = new TournamentTeam(teamId, tournament.getId());
			tournamentTeamRepository.save(tournamentTeam);

			var ranking = new Ranking(tournament.getId(), teamId, 0, 0, 0, 0, 0, 0, 0);
			rankingRepository.save(ranking);
		}

		if (tournamentDto.getType().equals("RoundRobin"))
			createRoundRobinTournamentMatches(tournament, tournamentDto.getTeamIds());
		else
			createSingleEliminationMatches(tournament, tournamentDto.getTeamIds());
	}

	private void isAuthorizedUser(String userEmail) {
		var user = userRepository.findByEmailAndActive(userEmail,"Y");
		if (user == null ||
			!user.getRole().equals("Admin"))
			throw new UnauthorizedException();
	}

	private void saveMatchOfTournament(Integer tournamentId, String tournamentType, String matchName, Integer teamId, Integer otherTeamId) {

		var matchId = matchRepository.getMaxId();
		var match = new Match(
				matchId == null ? 1 : ++matchId,
				tournamentType,
				"",
				"",
				""
		);
		matchRepository.save(match);

		var tournamentTeamMatch = new TournamentTeamMatch(
				match.getId(),
				teamId,
				otherTeamId,
				tournamentId,
				matchName
		);
		tournamentTeamMatchRepository.save(tournamentTeamMatch);
	}

	public void createRoundRobinTournamentMatches(Tournament tournament, List<Integer> listTeam)
	{
		final int DUMMY_TEAM_ID = 0;
		if (listTeam.size() % 2 != 0)
			listTeam.add(DUMMY_TEAM_ID); // If odd number of teams add a dummy

		var numTeams = listTeam.size();
		int numDays = (numTeams - 1); // Days needed to complete tournament
		int halfSize = numTeams / 2;

		var teams = new ArrayList<Integer>();

		teams.addAll(listTeam); // Add teams to List and remove the first team
		teams.remove(0);

		int teamsSize = teams.size();

		var result = new ArrayList<Match>();

		for (int day = 0; day < numDays; day++)
		{
			var goRoundMatchName = Integer.toString(day + 1);
			var backRoundMatchName = Integer.toString(2 * (day + 1));

			for (int idx = 1; idx < halfSize; idx++)
			{
				int firstTeam = (day + idx) % teamsSize;
				int secondTeam = (day  + teamsSize - idx) % teamsSize;

				if (teams.get(firstTeam) == DUMMY_TEAM_ID ||
					teams.get(secondTeam) == DUMMY_TEAM_ID)
					continue;

				// create go round match
				saveMatchOfTournament(tournament.getId(), tournament.getType(), goRoundMatchName, teams.get(firstTeam), teams.get(secondTeam));
				// create back round match
				saveMatchOfTournament(tournament.getId(), tournament.getType(), backRoundMatchName, teams.get(firstTeam), teams.get(secondTeam));
			}

			int teamIdx = day % teamsSize;
			if (teams.get(teamIdx) == DUMMY_TEAM_ID ||
					listTeam.get(0) == DUMMY_TEAM_ID)
				continue;

			// create go round match
			saveMatchOfTournament(tournament.getId(), tournament.getType(), goRoundMatchName, teams.get(teamIdx), listTeam.get(0));
			// create back round match
			saveMatchOfTournament(tournament.getId(), tournament.getType(), backRoundMatchName, teams.get(teamIdx), listTeam.get(0));
		}
	}

	private boolean isPowerOfTwo(int x) {
		/* First x in the below expression is  for the case when x is 0 */
		return x != 0 && ((x & (x - 1)) == 0);
	}

	private void createSingleEliminationMatches(Tournament tournament, List<Integer> teamIds) {
		var numberOfTeams = teamIds.size();
		if (!isPowerOfTwo(numberOfTeams) ||
			numberOfTeams > MAX_NUMBER_OF_TEAMS_FOR_SINGLE_ELIMINATION_TOURNAMENT)
			throw new NumberOfTeamsTournamentException();

		var teamsIterator = teamIds.listIterator();
		var matchName = 0;
		do {
			saveMatchOfTournament(tournament.getId(), "E", Integer.toString(++matchName), teamsIterator.next(), teamsIterator.next());

		} while (teamsIterator.hasNext());
	}
}
