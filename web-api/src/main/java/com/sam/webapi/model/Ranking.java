package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(RankingPK.class)
public class Ranking {
	private int tournamentId;
	private int teamId;
	private Integer score;
	private Integer wonMatches;
	private Integer lostMatches;
	private Integer tiedMatches;
	private Integer playedMatches;
	private Integer goalsMade;
	private Integer goalsSuffered;
	private Tournament tournamentByTournamentId;

	@Id
	@Column(name = "TOURNAMENT_ID", nullable = false)
	public int getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(int tournamentId) {
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

	@Basic
	@Column(name = "SCORE", nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Basic
	@Column(name = "WON_MATCHES", nullable = false)
	public Integer getWonMatches() {
		return wonMatches;
	}

	public void setWonMatches(Integer wonMatches) {
		this.wonMatches = wonMatches;
	}

	@Basic
	@Column(name = "LOST_MATCHES", nullable = false)
	public Integer getLostMatches() {
		return lostMatches;
	}

	public void setLostMatches(Integer lostMatches) {
		this.lostMatches = lostMatches;
	}

	@Basic
	@Column(name = "TIED_MATCHES", nullable = false)
	public Integer getTiedMatches() {
		return tiedMatches;
	}

	public void setTiedMatches(Integer tiedMatches) {
		this.tiedMatches = tiedMatches;
	}

	@Basic
	@Column(name = "PLAYED_MATCHES", nullable = false)
	public Integer getPlayedMatches() {
		return playedMatches;
	}

	public void setPlayedMatches(Integer playedMatches) {
		this.playedMatches = playedMatches;
	}

	@Basic
	@Column(name = "GOALS_MADE", nullable = false)
	public Integer getGoalsMade() {
		return goalsMade;
	}

	public void setGoalsMade(Integer goalsMade) {
		this.goalsMade = goalsMade;
	}

	@Basic
	@Column(name = "GOALS_SUFFERED", nullable = false)
	public Integer getGoalsSuffered() {
		return goalsSuffered;
	}

	public void setGoalsSuffered(Integer goalsSuffered) {
		this.goalsSuffered = goalsSuffered;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ranking ranking = (Ranking) o;
		return tournamentId == ranking.tournamentId && teamId == ranking.teamId && Objects.equals(score, ranking.score) && Objects.equals(wonMatches, ranking.wonMatches) && Objects.equals(lostMatches, ranking.lostMatches) && Objects.equals(tiedMatches, ranking.tiedMatches) && Objects.equals(playedMatches, ranking.playedMatches) && Objects.equals(goalsMade, ranking.goalsMade) && Objects.equals(goalsSuffered, ranking.goalsSuffered);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tournamentId, teamId, score, wonMatches, lostMatches, tiedMatches, playedMatches, goalsMade, goalsSuffered);
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
