package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Referee;
import org.springframework.data.repository.CrudRepository;

public interface RefereeRepository extends CrudRepository<Referee, Integer> {
}
