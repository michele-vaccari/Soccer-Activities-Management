package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

public class ReportDto {
	int id;
	String tournamentName;
	String teamName;
	String otherTeamName;
	String matchDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String refereeName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String refereeSurname;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String place;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String result;
	boolean compiled;

	public ReportDto() { }

	public ReportDto(int id,
					 String tournamentName,
					 String teamName,
					 String otherTeamName,
					 String matchDate,
					 String result,
					 boolean compiled) {
		this.id = id;
		this.tournamentName = tournamentName;
		this.teamName = teamName;
		this.otherTeamName = otherTeamName;
		this.matchDate = matchDate;
		this.result = result;
		this.compiled = compiled;
	}

	public ReportDto(int id,
					 String tournamentName,
					 String teamName,
					 String otherTeamName,
					 String matchDate,
					 String refereeName,
					 String refereeSurname,
					 String place,
					 String result,
					 boolean compiled) {
		this.id = id;
		this.tournamentName = tournamentName;
		this.teamName = teamName;
		this.otherTeamName = otherTeamName;
		this.matchDate = matchDate;
		this.refereeName = refereeName;
		this.refereeSurname = refereeSurname;
		this.place = place;
		this.result = result;
		this.compiled = compiled;
	}

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

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isCompiled() {
		return compiled;
	}

	public void setCompiled(boolean compiled) {
		this.compiled = compiled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReportDto reportDto = (ReportDto) o;
		return id == reportDto.id && compiled == reportDto.compiled && Objects.equals(tournamentName, reportDto.tournamentName) && Objects.equals(teamName, reportDto.teamName) && Objects.equals(otherTeamName, reportDto.otherTeamName) && Objects.equals(matchDate, reportDto.matchDate) && Objects.equals(refereeName, reportDto.refereeName) && Objects.equals(refereeSurname, reportDto.refereeSurname) && Objects.equals(place, reportDto.place) && Objects.equals(result, reportDto.result);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tournamentName, teamName, otherTeamName, matchDate, refereeName, refereeSurname, place, result, compiled);
	}
}
