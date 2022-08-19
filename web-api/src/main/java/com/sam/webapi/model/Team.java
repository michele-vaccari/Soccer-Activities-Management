package com.sam.webapi.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TEAM", schema = "SAM")
public class Team {
	private int id;
	private int teamManagerId;
	private String name;
	private String description;
	private String headquarters;
	private String sponsorName;
	private TeamManager teamManagerByTeamManagerId;
	private List<Player> playersById;

	public Team() { }

	public Team(int id,
				int teamManagerId,
				String name,
				String description,
				String headquarters,
				String sponsorName) {
		this.id = id;
		this.teamManagerId = teamManagerId;
		this.name = name;
		this.description = description;
		this.headquarters = headquarters;
		this.sponsorName = sponsorName;
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
	@Column(name = "TEAM_MANAGER_ID")
	public int getTeamManagerId() {
		return teamManagerId;
	}

	public void setTeamManagerId(int teamManagerId) {
		this.teamManagerId = teamManagerId;
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
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "HEADQUARTERS")
	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}

	@Basic
	@Column(name = "SPONSOR_NAME")
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
		Team team = (Team) o;
		return id == team.id && teamManagerId == team.teamManagerId && Objects.equals(name, team.name) && Objects.equals(description, team.description) && Objects.equals(headquarters, team.headquarters) && Objects.equals(sponsorName, team.sponsorName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teamManagerId, name, description, headquarters, sponsorName);
	}

	@OneToOne
	@JoinColumn(name = "TEAM_MANAGER_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public TeamManager getTeamManagerByTeamManagerId() {
		return teamManagerByTeamManagerId;
	}

	public void setTeamManagerByTeamManagerId(TeamManager teamManagerByTeamManagerId) {
		this.teamManagerByTeamManagerId = teamManagerByTeamManagerId;
	}

	@OneToMany(mappedBy = "teamByTeamId")
	public List<Player> getPlayersById() {
		return playersById;
	}

	public void setPlayersById(List<Player> playersById) {
		this.playersById = playersById;
	}
}
