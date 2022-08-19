package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class TeamDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String description;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String headquarters;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sponsorName;

	public TeamDto() { }

	public TeamDto(int id,
				   String name,
				   String description,
				   String headquarters,
				   String sponsorName) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.headquarters = headquarters;
		this.sponsorName = sponsorName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeamDto teamDto = (TeamDto) o;
		return id == teamDto.id && name.equals(teamDto.name) && Objects.equals(description, teamDto.description) && Objects.equals(headquarters, teamDto.headquarters) && Objects.equals(sponsorName, teamDto.sponsorName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, headquarters, sponsorName);
	}
}
