package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TEAM_MANAGER", schema = "SAM")
public class TeamManager {
	private int id;
	private Team teamById;
	private RegisteredUser registeredUserById;

	public TeamManager() { }

	public TeamManager(int id) {
		this.id = id;
	}

	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeamManager that = (TeamManager) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@OneToOne(mappedBy = "teamManagerByTeamManagerId")
	public Team getTeamById() {
		return teamById;
	}

	public void setTeamById(Team teamById) {
		this.teamById = teamById;
	}

	@OneToOne
	@JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
	public RegisteredUser getRegisteredUserById() {
		return registeredUserById;
	}

	public void setRegisteredUserById(RegisteredUser registeredUserById) {
		this.registeredUserById = registeredUserById;
	}
}
