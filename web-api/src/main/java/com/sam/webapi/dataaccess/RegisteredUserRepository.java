package com.sam.webapi.dataaccess;

import com.sam.webapi.model.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Integer> {
}
