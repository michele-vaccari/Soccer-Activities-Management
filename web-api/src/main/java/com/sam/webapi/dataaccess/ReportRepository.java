package com.sam.webapi.dataaccess;

import com.sam.webapi.model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Integer> {

	@Query(value = "SELECT MAX(Id) FROM SAM.REPORT", nativeQuery=true)
	Integer getMaxId();
}
