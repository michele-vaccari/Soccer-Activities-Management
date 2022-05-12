package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Tournament;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.TOURNAMENT", nativeQuery=true)
	Integer getMaxId();
}
