package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class MatchDto {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer teamId;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer otherTeamId;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String teamName;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String otherTeamName;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer reportId;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private boolean teamLineupSubmitted;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private boolean otherTeamLineupSubmitted;

	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String date;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String place;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer refereeId;

	public MatchDto() { }

	public MatchDto(int id,
					String name,
					Integer teamId,
					Integer otherTeamId,
					String teamName,
					String otherTeamName,
					Integer reportId,
					boolean teamLineupSubmitted,
					boolean otherTeamLineupSubmitted) {
		this.id = id;
		this.name = name;
		this.teamId = teamId;
		this.otherTeamId = otherTeamId;
		this.teamName = teamName;
		this.otherTeamName = otherTeamName;
		this.reportId = reportId;
		this.teamLineupSubmitted = teamLineupSubmitted;
		this.otherTeamLineupSubmitted = otherTeamLineupSubmitted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getOtherTeamId() {
		return otherTeamId;
	}

	public void setOtherTeamId(Integer otherTeamId) {
		this.otherTeamId = otherTeamId;
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

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public boolean isTeamLineupSubmitted() {
		return teamLineupSubmitted;
	}

	public void setTeamLineupSubmitted(boolean teamLineupSubmitted) {
		this.teamLineupSubmitted = teamLineupSubmitted;
	}

	public boolean isOtherTeamLineupSubmitted() {
		return otherTeamLineupSubmitted;
	}

	public void setOtherTeamLineupSubmitted(boolean otherTeamLineupSubmitted) {
		this.otherTeamLineupSubmitted = otherTeamLineupSubmitted;
	}

	public String getDate() {
		return date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(Integer refereeId) {
		this.refereeId = refereeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MatchDto matchDto = (MatchDto) o;
		return id == matchDto.id && teamLineupSubmitted == matchDto.teamLineupSubmitted && otherTeamLineupSubmitted == matchDto.otherTeamLineupSubmitted && Objects.equals(name, matchDto.name) && Objects.equals(teamId, matchDto.teamId) && Objects.equals(otherTeamId, matchDto.otherTeamId) && Objects.equals(teamName, matchDto.teamName) && Objects.equals(otherTeamName, matchDto.otherTeamName) && Objects.equals(reportId, matchDto.reportId) && Objects.equals(date, matchDto.date) && Objects.equals(place, matchDto.place) && Objects.equals(refereeId, matchDto.refereeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, teamId, otherTeamId, teamName, otherTeamName, reportId, teamLineupSubmitted, otherTeamLineupSubmitted, date, place, refereeId);
	}
}
