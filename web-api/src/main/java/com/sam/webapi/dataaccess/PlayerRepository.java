package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.PLAYER", nativeQuery=true)
	Integer getMaxId();

	Iterable<Player> findByTeamIdAndActive(Integer teamId, String active);
}
