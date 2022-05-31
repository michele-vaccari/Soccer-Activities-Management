package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReportRepository extends CrudRepository<Report, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.REPORT", nativeQuery=true)
	Integer getMaxId();

	Optional<Report> findByMatchId(Integer integer);
}
