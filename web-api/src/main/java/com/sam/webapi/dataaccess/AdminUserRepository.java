package com.sam.webapi.dataaccess;

import com.sam.webapi.model.AdminUser;
import org.springframework.data.repository.CrudRepository;

public interface AdminUserRepository extends CrudRepository<AdminUser, Integer> {
}
