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
	private String team;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String otherTeam;

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
					String team,
					String otherTeam) {
		this.id = id;
		this.name = name;
		this.team = team;
		this.otherTeam = otherTeam;
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getOtherTeam() {
		return otherTeam;
	}

	public void setOtherTeam(String otherTeam) {
		this.otherTeam = otherTeam;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
		return id == matchDto.id && name.equals(matchDto.name) && team.equals(matchDto.team) && otherTeam.equals(matchDto.otherTeam);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, team, otherTeam);
	}
}
