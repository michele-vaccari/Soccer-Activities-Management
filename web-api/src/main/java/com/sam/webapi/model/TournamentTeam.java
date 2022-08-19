package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOURNAMENT_TEAM", schema = "SAM")
@IdClass(TournamentTeamPK.class)
public class TournamentTeam {
	private int teamId;
	private int tournamentId;
	private Tournament tournamentByTournamentId;

	public TournamentTeam() { }

	public TournamentTeam(int teamId, int tournamentId) {
		this.teamId = teamId;
		this.tournamentId = tournamentId;
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
	@Column(name = "TOURNAMENT_ID", nullable = false)
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
		TournamentTeam that = (TournamentTeam) o;
		return teamId == that.teamId && tournamentId == that.tournamentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(teamId, tournamentId);
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
