package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PLAYER_REPORT", schema = "SAM")
@IdClass(PlayerReportPK.class)
public class PlayerReport {
	private int reportId;
	private int playerId;
	private Integer goal;
	private Integer admonitions;
	private String ejection;
	private Report reportByReportId;

	public PlayerReport() { }

	public PlayerReport(int reportId,
						int playerId,
						Integer goal,
						Integer admonitions,
						String ejection) {
		this.reportId = reportId;
		this.playerId = playerId;
		this.goal = goal;
		this.admonitions = admonitions;
		this.ejection = ejection;
	}

	@Id
	@Column(name = "REPORT_ID", nullable = false)
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
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
	@Column(name = "GOAL")
	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	@Basic
	@Column(name = "ADMONITIONS")
	public Integer getAdmonitions() {
		return admonitions;
	}

	public void setAdmonitions(Integer admonitions) {
		this.admonitions = admonitions;
	}

	@Basic
	@Column(name = "EJECTION")
	public String getEjection() {
		return ejection;
	}

	public void setEjection(String ejection) {
		this.ejection = ejection;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerReport that = (PlayerReport) o;
		return reportId == that.reportId && playerId == that.playerId && Objects.equals(goal, that.goal) && Objects.equals(admonitions, that.admonitions) && Objects.equals(ejection, that.ejection);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reportId, playerId, goal, admonitions, ejection);
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
