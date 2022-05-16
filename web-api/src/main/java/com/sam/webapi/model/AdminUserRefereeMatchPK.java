package com.sam.webapi.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AdminUserRefereeMatchPK implements Serializable {
	private int matchId;
	private int refereeId;
	private int adminUserId;

	@Column(name = "MATCH_ID", nullable = false)
	@Id
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Column(name = "REFEREE_ID", nullable = false)
	@Id
	public int getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(int refereeId) {
		this.refereeId = refereeId;
	}

	@Column(name = "ADMIN_USER_ID", nullable = false)
	@Id
	public int getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AdminUserRefereeMatchPK that = (AdminUserRefereeMatchPK) o;
		return matchId == that.matchId && refereeId == that.refereeId && adminUserId == that.adminUserId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchId, refereeId, adminUserId);
	}
}
