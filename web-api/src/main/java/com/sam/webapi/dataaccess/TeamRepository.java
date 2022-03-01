package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.TEAM", nativeQuery=true)
	Integer getMaxId();

	Optional<Team> findByTeamManagerId(Integer id);
}
