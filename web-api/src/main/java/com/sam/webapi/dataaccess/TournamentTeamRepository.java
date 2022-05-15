package com.sam.webapi.dataaccess;

import com.sam.webapi.model.TournamentTeam;
import com.sam.webapi.model.TournamentTeamPK;
import org.springframework.data.repository.CrudRepository;

public interface TournamentTeamRepository extends CrudRepository<TournamentTeam, TournamentTeamPK> {

	Iterable<TournamentTeam> findAllByTeamId(Integer id);
}
