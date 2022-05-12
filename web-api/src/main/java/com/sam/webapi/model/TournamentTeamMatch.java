package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOURNAMENT_TEAM_MATCH", schema = "SAM")
@IdClass(TournamentTeamMatchPK.class)
public class TournamentTeamMatch {
	private int matchId;
	private int teamId;
	private int otherTeamId;
	private int tournamentId;
	private String matchName;
	private Match matchByMatchId;
	private Tournament tournamentByTournamentId;

	public TournamentTeamMatch() { }

	public TournamentTeamMatch(int matchId,
							   int teamId,
							   int otherTeamId,
							   int tournamentId,
							   String matchName) {
		this.matchId = matchId;
		this.teamId = teamId;
		this.otherTeamId = otherTeamId;
		this.tournamentId = tournamentId;
		this.matchName = matchName;
	}

	@Id
	@Column(name = "MATCH_ID", nullable = false)
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Id
	@Column(name = "TEAM_ID", nullable = false)
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Id
	@Column(name = "OTHER_TEAM_ID", nullable = false)
	public int getOtherTeamId() {
		return otherTeamId;
	}

	public void setOtherTeamId(int otherTeamId) {
		this.otherTeamId = otherTeamId;
	}

	@Id
	@Column(name = "TOURNAMENT_ID", nullable = false)
	public int getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}

	@Basic
	@Column(name = "MATCH_NAME", nullable = false)
	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TournamentTeamMatch that = (TournamentTeamMatch) o;
		return matchId == that.matchId && teamId == that.teamId && otherTeamId == that.otherTeamId && tournamentId == that.tournamentId && Objects.equals(matchName, that.matchName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchId, teamId, otherTeamId, tournamentId, matchName);
	}

	@ManyToOne
	@JoinColumn(name = "MATCH_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public Match getMatchByMatchId() {
		return matchByMatchId;
	}

	public void setMatchByMatchId(Match matchByMatchId) {
		this.matchByMatchId = matchByMatchId;
	}

	@ManyToOne
	@JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public Tournament getTournamentByTournamentId() {
		return tournamentByTournamentId;
	}

	public void setTournamentByTournamentId(Tournament tournamentByTournamentId) {
		this.tournamentByTournamentId = tournamentByTournamentId;
	}
}
