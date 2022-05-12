package com.sam.webapi.dataaccess;

import com.sam.webapi.model.TournamentTeamMatch;
import com.sam.webapi.model.TournamentTeamMatchPK;
import org.springframework.data.repository.CrudRepository;

public interface TournamentTeamMatchRepository extends CrudRepository<TournamentTeamMatch, TournamentTeamMatchPK> {
}
