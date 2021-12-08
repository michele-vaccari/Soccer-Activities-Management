package com.sam.webapi.dataaccess;

import com.sam.webapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
