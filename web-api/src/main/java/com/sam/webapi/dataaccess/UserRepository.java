package com.sam.webapi.dataaccess;

import com.sam.webapi.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	boolean existsByEmail(String email);
	boolean existsByEmailAndIdNot(String email, Integer id);

	@Query(value = "SELECT MAX(Id) FROM Users.User", nativeQuery=true)
	Integer getMaxId();

	@Modifying
	@Query(value = "UPDATE Users.User SET Active='N' WHERE Id=?1", nativeQuery=true)
	void deactivateUserById(Integer id);

	User findByEmailAndActive(String email, String active);
}
