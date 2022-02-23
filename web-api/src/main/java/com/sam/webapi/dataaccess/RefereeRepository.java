package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Referee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RefereeRepository extends CrudRepository<Referee, Integer> {
	@Modifying
	@Query(value = "SELECT * FROM SAM.USER AS U, SAM.REGISTERED_USER AS RU, SAM.REFEREE AS R WHERE U.ACTIVE = 'Y' AND U.ID = RU.ID AND RU.ID = R.ID", nativeQuery=true)
	Iterable<Referee> findAllActive();
}
