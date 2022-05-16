package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TEAM_PLAYER_REPORT", schema = "SAM")
@IdClass(TeamPlayerReportPK.class)
public class TeamPlayerReport {
	private int reportId;
	private int teamId;
	private int playerId;
	private String reserve;
	private Report reportByReportId;

	@Id
	@Column(name = "REPORT_ID", nullable = false)
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
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
	@Column(name = "PLAYER_ID", nullable = false)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic
	@Column(name = "RESERVE", nullable = false)
	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeamPlayerReport that = (TeamPlayerReport) o;
		return reportId == that.reportId && teamId == that.teamId && playerId == that.playerId && Objects.equals(reserve, that.reserve);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reportId, teamId, playerId, reserve);
	}

	@ManyToOne
	@JoinColumn(name = "REPORT_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	public Report getReportByReportId() {
		return reportByReportId;
	}

	public void setReportByReportId(Report reportByReportId) {
		this.reportByReportId = reportByReportId;
	}
}
