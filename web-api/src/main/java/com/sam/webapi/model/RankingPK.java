package com.sam.webapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RankingPK implements Serializable {
	private int tournamentId;
	private int teamId;

	public RankingPK() { }

	public RankingPK(int tournamentId,
					 int teamId) {
		this.tournamentId = tournamentId;
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

	@Column(name = "TEAM_ID", nullable = false)
	@Id
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RankingPK rankingPK = (RankingPK) o;
		return tournamentId == rankingPK.tournamentId && teamId == rankingPK.teamId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tournamentId, teamId);
	}
}
