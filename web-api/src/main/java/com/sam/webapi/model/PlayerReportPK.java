package com.sam.webapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PlayerReportPK implements Serializable {
	private int reportId;
	private int playerId;

	@Column(name = "REPORT_ID", nullable = false)
	@Id
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	@Column(name = "PLAYER_ID", nullable = false)
	@Id
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerReportPK that = (PlayerReportPK) o;
		return reportId == that.reportId && playerId == that.playerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reportId, playerId);
	}
}
