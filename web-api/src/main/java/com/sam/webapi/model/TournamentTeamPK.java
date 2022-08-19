package com.sam.webapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TournamentTeamPK implements Serializable {
	private int teamId;
	private int tournamentId;

	@Column(name = "TEAM_ID", nullable = false)
	@Id
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Column(name = "TOURNAMENT_ID", nullable = false)
	@Id
	public int getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TournamentTeamPK that = (TournamentTeamPK) o;
		return teamId == that.teamId && tournamentId == that.tournamentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(teamId, tournamentId);
	}
}
