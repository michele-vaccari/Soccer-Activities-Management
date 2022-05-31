package com.sam.webapi.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.*;
import com.sam.webapi.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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

		var tournaments = tournamentRepository.findAll();

		var shortTournamentsDto =  new ArrayList<ShortTournamentDto>();
		tournaments.forEach(tournament -> shortTournamentsDto.add(convertEntityToShortDto(tournament)));

		return shortTournamentsDto;
	}

	@Override
	@Transactional
	public TournamentDto getTournament(Integer id) {

		var tournament = tournamentRepository.findById(id);

		if (tournament.isEmpty())
			throw new TournamentNotFoundException();

		return  convertEntityToDto(tournament.get());
	}

	private ShortTournamentDto convertEntityToShortDto(Tournament tournament) {
		return new ShortTournamentDto(
				tournament.getId(),
				tournament.getName(),
				tournament.getType().equals("R") ? "RoundRobin" : "SingleElimination"
		);
	}

	private TournamentDto convertEntityToDto(Tournament tournament) {

		var isRoundRobinTournament = tournament.getType().equals("R");

		var tournamentTeamMatches = tournament.getTournamentTeamMatchesById();

		// create roundRobin matches
		var firstRoundMatches = new HashMap<Integer, ArrayList<MatchDto>>();
		var secondRoundMatches = new HashMap<Integer, ArrayList<MatchDto>>();

		var eighthFinalsMatches = new HashMap<Integer, ArrayList<MatchDto>>();
		var quarterFinalsMatches = new HashMap<Integer, ArrayList<MatchDto>>();
		var semifinalMatches = new HashMap<Integer, ArrayList<MatchDto>>();
		var finalMatch = new MatchDto();

		if (isRoundRobinTournament) {
			var numberOfMatch = tournamentTeamMatches.size();

			for (var tournamentTeamMatch : tournamentTeamMatches) {

				var match = tournamentTeamMatch.getMatchByMatchId();
				var teamId = tournamentTeamMatch.getTeamId();
				var team = teamRepository.getById(teamId).get();
				var otherTeamId = tournamentTeamMatch.getOtherTeamId();
				var otherTeam = teamRepository.getById(otherTeamId).get();
				var report = match.getReportsById();

				var matchDto = new MatchDto(
						match.getId(),
						null,
						tournamentTeamMatch.getMatchName(),
						teamId,
						otherTeamId,
						team.getName(),
						otherTeam.getName(),
						report == null ? null : report.getId(),
						false, //teamLineupSubmitted
						false //otherTeamLineupSubmitted
				);

				var matchNumber = Integer.parseInt(tournamentTeamMatch.getMatchName());
				var isFirstDayMatch = matchNumber <= (numberOfMatch / 4);

				if (isFirstDayMatch)
					addMatchToMap(firstRoundMatches, matchNumber, matchDto);
				else
					addMatchToMap(secondRoundMatches, matchNumber, matchDto);
			}
		}
		else { // create single elimination matches

			var numberOfTeams = tournamentTeamRepository.findTeamIdsByTournamentId(tournament.getId()).size();

			var hasEighthFinals = numberOfTeams >= 16;
			var hasQuarterFinals = numberOfTeams >= 8;
			var hasSemifinal = numberOfTeams > 2;

			for (var tournamentTeamMatch : tournamentTeamMatches) {

				var match = tournamentTeamMatch.getMatchByMatchId();
				var teamId = tournamentTeamMatch.getTeamId();
				var team = teamRepository.getById(teamId).get();
				var otherTeamId = tournamentTeamMatch.getOtherTeamId();
				var otherTeam = teamRepository.getById(otherTeamId).get();
				var report = match.getReportsById();

				var matchDto = new MatchDto(
						match.getId(),
						null,
						tournamentTeamMatch.getMatchName(),
						teamId,
						otherTeamId,
						team.getName(),
						otherTeam.getName(),
						report == null ? null : report.getId(),
						false, //teamLineupSubmitted
						false //otherTeamLineupSubmitted
				);

				var matchNumber = Integer.parseInt(tournamentTeamMatch.getMatchName());

				if (hasEighthFinals == false &&
					hasQuarterFinals == false &&
					hasSemifinal == false) {
					finalMatch = matchDto;
				} else if (hasEighthFinals == false &&
						   hasQuarterFinals == false &&
						   hasSemifinal == true) {
					var isFinalMatch = matchNumber == 3;

					finalMatch = new MatchDto();
					finalMatch.setMatchName("3");

					if (isFinalMatch)
						finalMatch = matchDto;
					else {
						var matches = new ArrayList<MatchDto>();
						if (semifinalMatches.containsKey(matchNumber))
							matches = semifinalMatches.get(matchNumber);
						matches.add(matchDto);
						semifinalMatches.put(matchNumber, matches);
					}
				} else if (hasEighthFinals == false &&
						hasQuarterFinals == true &&
						hasSemifinal == true) {
					var isFinalMatch = matchNumber == 7;
					var isSemifinalMatch = matchNumber == 5 &&
										   matchNumber == 6;

					finalMatch = new MatchDto();
					finalMatch.setMatchName("7");
					semifinalMatches.put(5, new ArrayList<MatchDto>());
					semifinalMatches.put(6, new ArrayList<MatchDto>());

					if (isFinalMatch)
						finalMatch = matchDto;
					else if (isSemifinalMatch)
						addMatchToMap(semifinalMatches, matchNumber, matchDto);
					else
						addMatchToMap(quarterFinalsMatches, matchNumber, matchDto);
				} else if (hasEighthFinals == true &&
						   hasQuarterFinals == true &&
						   hasSemifinal == true) {
					var isFinalMatch = matchNumber == 15;
					var isSemifinalMatch = matchNumber == 13 &&
							               matchNumber == 14;
					var isQuaterMatch = matchNumber >= 9 && matchNumber <= 12;

					finalMatch = new MatchDto();
					finalMatch.setMatchName("15");
					semifinalMatches.put(13, new ArrayList<MatchDto>());
					semifinalMatches.put(14, new ArrayList<MatchDto>());
					for (var index = 9; matchNumber >= 9 && matchNumber <= 12; ++index)
						quarterFinalsMatches.put(index, new ArrayList<MatchDto>());

					if (isFinalMatch)
						finalMatch = matchDto;
					else if (isSemifinalMatch)
						addMatchToMap(semifinalMatches, matchNumber, matchDto);
					else if (isQuaterMatch)
						addMatchToMap(quarterFinalsMatches, matchNumber, matchDto);
					else
						addMatchToMap(eighthFinalsMatches, matchNumber, matchDto);
				}
			}
		}

		// create ranking

		var rankingLines = tournament.getRankingsById();
		rankingLines.sort((o1, o2) -> o1.getScore().compareTo(o2.getScore()));
		var rankingLinesDto =  new ArrayList<RankingLineDto>();
		var position = 0;
		for (var rankingLine : rankingLines) {
			var team = teamRepository.getById(rankingLine.getTeamId());
			rankingLinesDto.add(createRankingLineDto(tournament.getType(), ++position, team.get().getName(), rankingLine));
		}

		// return tournamentDto
		if (isRoundRobinTournament)
			return new TournamentDto(
					tournament.getId(),
					tournament.getName(),
					tournament.getType(),
					tournament.getDescription(),
					firstRoundMatches,
					secondRoundMatches,
					rankingLinesDto);
		else
			return new TournamentDto(
					tournament.getId(),
					tournament.getName(),
					tournament.getType(),
					tournament.getDescription(),
					eighthFinalsMatches,
					quarterFinalsMatches,
					semifinalMatches,
					finalMatch,
					rankingLinesDto);
	}

	private void addMatchToMap(HashMap<Integer, ArrayList<MatchDto>> map, Integer key, MatchDto matchDto) {
		var matches = new ArrayList<MatchDto>();
		if (map.containsKey(key))
			matches = map.get(key);
		matches.add(matchDto);
		map.put(key, matches);
	}

	private RankingLineDto createRankingLineDto(String tournamentType, Integer position, String teamName, Ranking ranking) {

		if (tournamentType.equals("R"))
			return new RankingLineDto(
					position,
					teamName,
					ranking.getScore(),
					ranking.getPlayedMatches(),
					ranking.getWonMatches(),
					ranking.getLostMatches(),
					ranking.getTiedMatches(),
					ranking.getGoalsMade(),
					ranking.getGoalsSuffered()
			);
		else
			return new RankingLineDto(
					position,
					teamName,
					ranking.getPlayedMatches(),
					ranking.getWonMatches(),
					ranking.getLostMatches(),
					ranking.getGoalsMade(),
					ranking.getGoalsSuffered()
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

		for (int day = 0; day < numDays; day++)
		{
			var goRoundMatchName = Integer.toString(day + 1);
			var backRoundMatchName = Integer.toString(numDays + day + 1);

			for (int idx = 1; idx < halfSize; idx++)
			{
				int firstTeamIndex = (day + idx) % teamsSize;
				int secondTeamIndex = (day  + teamsSize - idx) % teamsSize;

				var firstTeamId = teams.get(firstTeamIndex);
				var secondTeamId = teams.get(secondTeamIndex);

				if (firstTeamId== DUMMY_TEAM_ID || secondTeamId == DUMMY_TEAM_ID)
					continue;

				// create go round match
				saveMatchOfTournament(tournament.getId(), tournament.getType(), goRoundMatchName, firstTeamId, secondTeamId);
				// create back round match
				saveMatchOfTournament(tournament.getId(), tournament.getType(), backRoundMatchName, secondTeamId, firstTeamId);
			}

			int teamIdx = day % teamsSize;
			if (teams.get(teamIdx) == DUMMY_TEAM_ID ||
					listTeam.get(0) == DUMMY_TEAM_ID)
				continue;

			// create go round match
			saveMatchOfTournament(tournament.getId(), tournament.getType(), goRoundMatchName, teams.get(teamIdx), listTeam.get(0));
			// create back round match
			saveMatchOfTournament(tournament.getId(), tournament.getType(), backRoundMatchName, listTeam.get(0), teams.get(teamIdx));
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
