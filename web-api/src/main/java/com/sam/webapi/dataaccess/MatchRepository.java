package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.MATCH", nativeQuery=true)
	Integer getMaxId();
}
