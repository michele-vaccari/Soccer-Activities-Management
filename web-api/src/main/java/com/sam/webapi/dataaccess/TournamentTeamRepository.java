package com.sam.webapi.dataaccess;

import com.sam.webapi.model.TournamentTeam;
import com.sam.webapi.model.TournamentTeamPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentTeamRepository extends CrudRepository<TournamentTeam, TournamentTeamPK> {

	Iterable<TournamentTeam> findAllByTeamId(Integer id);

	@Query(value = "SELECT Team_Id FROM SAM.TOURNAMENT_TEAM WHERE Tournament_Id=?1", nativeQuery = true)
	List<Integer> findTeamIdsByTournamentId(int tournamentId);
}
