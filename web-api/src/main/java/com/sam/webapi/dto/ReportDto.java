package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class ReportDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	int id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String tournamentName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String teamName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String otherTeamName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String result;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Iterable<PlayerReportDto> mainTeamPlayerReportsDto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Iterable<PlayerReportDto> reserveTeamPlayerReportsDto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Iterable<PlayerReportDto> mainOtherTeamPlayerReportsDto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Iterable<PlayerReportDto> reserveOtherTeamPlayerReportsDto;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	int autogoalTeam;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	int autogoalOtherTeam;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String matchPlace;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String matchDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String matchStartTime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String matchEndTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	int refereeId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String refereeName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	String refereeSurname;

	public ReportDto() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getOtherTeamName() {
		return otherTeamName;
	}

	public void setOtherTeamName(String otherTeamName) {
		this.otherTeamName = otherTeamName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Iterable<PlayerReportDto> getMainTeamPlayerReportsDto() {
		return mainTeamPlayerReportsDto;
	}

	public void setMainTeamPlayerReportsDto(Iterable<PlayerReportDto> mainTeamPlayerReportsDto) {
		this.mainTeamPlayerReportsDto = mainTeamPlayerReportsDto;
	}

	public Iterable<PlayerReportDto> getReserveTeamPlayerReportsDto() {
		return reserveTeamPlayerReportsDto;
	}

	public void setReserveTeamPlayerReportsDto(Iterable<PlayerReportDto> reserveTeamPlayerReportsDto) {
		this.reserveTeamPlayerReportsDto = reserveTeamPlayerReportsDto;
	}

	public Iterable<PlayerReportDto> getMainOtherTeamPlayerReportsDto() {
		return mainOtherTeamPlayerReportsDto;
	}

	public void setMainOtherTeamPlayerReportsDto(Iterable<PlayerReportDto> mainOtherTeamPlayerReportsDto) {
		this.mainOtherTeamPlayerReportsDto = mainOtherTeamPlayerReportsDto;
	}

	public Iterable<PlayerReportDto> getReserveOtherTeamPlayerReportsDto() {
		return reserveOtherTeamPlayerReportsDto;
	}

	public void setReserveOtherTeamPlayerReportsDto(Iterable<PlayerReportDto> reserveOtherTeamPlayerReportsDto) {
		this.reserveOtherTeamPlayerReportsDto = reserveOtherTeamPlayerReportsDto;
	}

	public int getAutogoalTeam() {
		return autogoalTeam;
	}

	public void setAutogoalTeam(int autogoalTeam) {
		this.autogoalTeam = autogoalTeam;
	}

	public int getAutogoalOtherTeam() {
		return autogoalOtherTeam;
	}

	public void setAutogoalOtherTeam(int autogoalOtherTeam) {
		this.autogoalOtherTeam = autogoalOtherTeam;
	}

	public String getMatchPlace() {
		return matchPlace;
	}

	public void setMatchPlace(String matchPlace) {
		this.matchPlace = matchPlace;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchStartTime() {
		return matchStartTime;
	}

	public void setMatchStartTime(String matchStartTime) {
		this.matchStartTime = matchStartTime;
	}

	public String getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(String matchEndTime) {
		this.matchEndTime = matchEndTime;
	}

	public int getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(int refereeId) {
		this.refereeId = refereeId;
	}

	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	public String getRefereeSurname() {
		return refereeSurname;
	}

	public void setRefereeSurname(String refereeSurname) {
		this.refereeSurname = refereeSurname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReportDto reportDto = (ReportDto) o;
		return id == reportDto.id && autogoalTeam == reportDto.autogoalTeam && autogoalOtherTeam == reportDto.autogoalOtherTeam && refereeId == reportDto.refereeId && Objects.equals(tournamentName, reportDto.tournamentName) && Objects.equals(teamName, reportDto.teamName) && Objects.equals(otherTeamName, reportDto.otherTeamName) && Objects.equals(result, reportDto.result) && Objects.equals(mainTeamPlayerReportsDto, reportDto.mainTeamPlayerReportsDto) && Objects.equals(reserveTeamPlayerReportsDto, reportDto.reserveTeamPlayerReportsDto) && Objects.equals(mainOtherTeamPlayerReportsDto, reportDto.mainOtherTeamPlayerReportsDto) && Objects.equals(reserveOtherTeamPlayerReportsDto, reportDto.reserveOtherTeamPlayerReportsDto) && Objects.equals(matchPlace, reportDto.matchPlace) && Objects.equals(matchDate, reportDto.matchDate) && Objects.equals(matchStartTime, reportDto.matchStartTime) && Objects.equals(matchEndTime, reportDto.matchEndTime) && Objects.equals(refereeName, reportDto.refereeName) && Objects.equals(refereeSurname, reportDto.refereeSurname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tournamentName, teamName, otherTeamName, result, mainTeamPlayerReportsDto, reserveTeamPlayerReportsDto, mainOtherTeamPlayerReportsDto, reserveOtherTeamPlayerReportsDto, autogoalTeam, autogoalOtherTeam, matchPlace, matchDate, matchStartTime, matchEndTime, refereeId, refereeName, refereeSurname);
	}
}
