package com.sam.webapi.dataaccess;

import com.sam.webapi.model.TeamManager;
import org.springframework.data.repository.CrudRepository;

public interface TeamManagerRepository extends CrudRepository<TeamManager, Integer> {
}
