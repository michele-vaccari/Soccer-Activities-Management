package com.sam.webapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TournamentTeamMatchPK implements Serializable {
	private int matchId;
	private int teamId;
	private int otherTeamId;
	private int tournamentId;

	@Column(name = "MATCH_ID", nullable = false)
	@Id
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Column(name = "TEAM_ID", nullable = false)
	@Id
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Column(name = "OTHER_TEAM_ID", nullable = false)
	@Id
	public int getOtherTeamId() {
		return otherTeamId;
	}

	public void setOtherTeamId(int otherTeamId) {
		this.otherTeamId = otherTeamId;
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
		TournamentTeamMatchPK that = (TournamentTeamMatchPK) o;
		return matchId == that.matchId && teamId == that.teamId && otherTeamId == that.otherTeamId && tournamentId == that.tournamentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchId, teamId, otherTeamId, tournamentId);
	}
}
