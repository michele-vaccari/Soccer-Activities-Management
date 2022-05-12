package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class MatchDto {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String team;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String otherTeam;

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
