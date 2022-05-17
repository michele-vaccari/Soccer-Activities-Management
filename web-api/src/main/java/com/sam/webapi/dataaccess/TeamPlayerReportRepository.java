package com.sam.webapi.dataaccess;

import com.sam.webapi.model.TeamPlayerReport;
import com.sam.webapi.model.TeamPlayerReportPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamPlayerReportRepository extends CrudRepository<TeamPlayerReport, TeamPlayerReportPK> {

	@Query(value = "SELECT DISTINCT Team_Id FROM SAM.TEAM_PLAYER_REPORT WHERE Report_Id=?1", nativeQuery = true)
	List<Integer> findDistinctTeamIdsByReportId(int id);
}
