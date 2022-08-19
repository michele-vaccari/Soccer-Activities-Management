package com.sam.webapi.service;

import com.sam.webapi.dto.LineupDto;
import com.sam.webapi.dto.ReportDto;
import com.sam.webapi.dto.ShortReportDto;

public interface ReportService {
	Iterable<ShortReportDto> getReports(String adminEmail);
	ReportDto getReport(Integer id);
	ReportDto getLineups(Integer id, String refereeEmail);
	void compileReport(Integer id, String refereeEmail, ReportDto report);
	void addLineupToReport(Integer id, String userEmail, LineupDto lineupDto);
}
