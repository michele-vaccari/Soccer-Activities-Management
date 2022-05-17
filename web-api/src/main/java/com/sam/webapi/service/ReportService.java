package com.sam.webapi.service;

import com.sam.webapi.dto.LineupDto;

public interface ReportService {
	void addLineupToReport(Integer id, String userEmail, LineupDto lineupDto);
}
