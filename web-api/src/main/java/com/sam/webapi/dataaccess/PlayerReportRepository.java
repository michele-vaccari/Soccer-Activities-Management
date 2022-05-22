package com.sam.webapi.dataaccess;

import com.sam.webapi.model.PlayerReport;
import com.sam.webapi.model.PlayerReportPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerReportRepository extends CrudRepository<PlayerReport, PlayerReportPK> {
}
