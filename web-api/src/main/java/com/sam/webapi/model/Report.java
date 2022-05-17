package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "REPORT", schema = "SAM")
public class Report {
	private int id;
	private int matchId;
	private int refereeId;
	private String matchStartTime;
	private String matchEndTime;
	private String result;
	private Match matchByMatchId;

	public Report() { }

	public Report(int id,
				  int matchId,
				  int refereeId) {
		this.id = id;
		this.matchId = matchId;
		this.refereeId = refereeId;
	}

	@Id
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "MATCH_ID", nullable = false)
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Basic
	@Column(name = "REFEREE_ID", nullable = false)
	public int getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(int refereeId) {
		this.refereeId = refereeId;
	}

	@Basic
	@Column(name = "MATCH_START_TIME")
	public String getMatchStartTime() {
		return matchStartTime;
	}

	public void setMatchStartTime(String matchStartTime) {
		this.matchStartTime = matchStartTime;
	}

	@Basic
	@Column(name = "MATCH_END_TIME")
	public String getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(String matchEndTime) {
		this.matchEndTime = matchEndTime;
	}

	@Basic
	@Column(name = "RESULT")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Report report = (Report) o;
		return id == report.id && matchId == report.matchId && refereeId == report.refereeId && Objects.equals(matchStartTime, report.matchStartTime) && Objects.equals(matchEndTime, report.matchEndTime) && Objects.equals(result, report.result);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, matchId, refereeId, matchStartTime, matchEndTime, result);
	}

	@OneToOne
	@JoinColumn(name = "MATCH_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public Match getMatchByMatchId() {
		return matchByMatchId;
	}

	public void setMatchByMatchId(Match matchByMatchId) {
		this.matchByMatchId = matchByMatchId;
	}
}
