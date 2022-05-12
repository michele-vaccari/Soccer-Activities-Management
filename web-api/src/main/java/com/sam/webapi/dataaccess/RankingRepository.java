package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Ranking;
import com.sam.webapi.model.RankingPK;
import org.springframework.data.repository.CrudRepository;

public interface RankingRepository extends CrudRepository<Ranking, RankingPK> {
}
