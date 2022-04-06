package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PLAYER", schema = "SAM")
public class Player {
	private int id;
	private int teamId;
	private String active;
	private Integer jerseyNumber;
	private String role;
	private String name;
	private String surname;
	private String birthDate;
	private String citizenship;
	private String description;
	private Integer goal;
	private Integer admonitions;
	private Integer ejections;
	private Team teamByTeamId;

	public Player() { }

	public Player(int id,
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

	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "TEAM_ID")
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Basic
	@Column(name = "ACTIVE", columnDefinition = "varchar(1)")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Basic
	@Column(name = "JERSEY_NUMBER")
	public Integer getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(Integer jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	@Basic
	@Column(name = "ROLE")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Basic
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "SURNAME")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Basic
	@Column(name = "BIRTH_DATE")
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@Basic
	@Column(name = "CITIZENSHIP")
	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Basic
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "GOAL")
	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	@Basic
	@Column(name = "ADMONITIONS")
	public Integer getAdmonitions() {
		return admonitions;
	}

	public void setAdmonitions(Integer admonitions) {
		this.admonitions = admonitions;
	}

	@Basic
	@Column(name = "EJECTIONS")
	public Integer getEjections() {
		return ejections;
	}

	public void setEjections(Integer ejections) {
		this.ejections = ejections;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return id == player.id && teamId == player.teamId && Objects.equals(active, player.active) && Objects.equals(jerseyNumber, player.jerseyNumber) && Objects.equals(role, player.role) && Objects.equals(name, player.name) && Objects.equals(surname, player.surname) && Objects.equals(birthDate, player.birthDate) && Objects.equals(citizenship, player.citizenship) && Objects.equals(description, player.description) && Objects.equals(goal, player.goal) && Objects.equals(admonitions, player.admonitions) && Objects.equals(ejections, player.ejections);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teamId, active, jerseyNumber, role, name, surname, birthDate, citizenship, description, goal, admonitions, ejections);
	}

	@ManyToOne
	@JoinColumn(name = "TEAM_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public Team getTeamByTeamId() {
		return teamByTeamId;
	}

	public void setTeamByTeamId(Team teamByTeamId) {
		this.teamByTeamId = teamByTeamId;
	}
}
