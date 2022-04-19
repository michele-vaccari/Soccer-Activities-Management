package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class PlayerDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	private int teamId;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private String active;
	private Integer jerseyNumber;
	private String role;
	private String name;
	private String surname;
	private String birthDate;
	private String citizenship;
	private String description;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private Integer goal;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private Integer admonitions;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private Integer ejections;

	public PlayerDto() { }

	public PlayerDto(int id,
					 int teamId,
					 String active,
					 Integer jerseyNumber,
					 String role,
					 String name,
					 String surname,
					 String birthDate,
					 String citizenship,
					 String description,
					 Integer goal,
					 Integer admonitions,
					 Integer ejections) {
		this.id = id;
		this.teamId = teamId;
		this.active = active;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.citizenship = citizenship;
		this.description = description;
		this.goal = goal;
		this.admonitions = admonitions;
		this.ejections = ejections;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Integer getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(Integer jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Integer getAdmonitions() {
		return admonitions;
	}

	public void setAdmonitions(Integer admonitions) {
		this.admonitions = admonitions;
	}

	public Integer getEjections() {
		return ejections;
	}

	public void setEjections(Integer ejections) {
		this.ejections = ejections;
	}

	@JsonIgnore
	public boolean isValid() {
		return this.teamId != 0 &&
			   this.jerseyNumber > 0 &&
			   this.role != null && !this.role.isEmpty() &&
			   this.name != null && !this.name.isEmpty() &&
			   this.surname != null && !this.surname.isEmpty() &&
			   this.birthDate != null && !this.birthDate.isEmpty() &&
			   this.citizenship != null && !this.birthDate.isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerDto playerDto = (PlayerDto) o;
		return id == playerDto.id && teamId == playerDto.teamId && active.equals(playerDto.active) && jerseyNumber.equals(playerDto.jerseyNumber) && role.equals(playerDto.role) && name.equals(playerDto.name) && surname.equals(playerDto.surname) && birthDate.equals(playerDto.birthDate) && citizenship.equals(playerDto.citizenship) && Objects.equals(description, playerDto.description) && goal.equals(playerDto.goal) && admonitions.equals(playerDto.admonitions) && ejections.equals(playerDto.ejections);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teamId, active, jerseyNumber, role, name, surname, birthDate, citizenship, description, goal, admonitions, ejections);
	}
}
