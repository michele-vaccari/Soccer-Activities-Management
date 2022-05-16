package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ADMIN_USER_REFEREE_MATCH", schema = "SAM")
@IdClass(AdminUserRefereeMatchPK.class)
public class AdminUserRefereeMatch {
	private int matchId;
	private int refereeId;
	private int adminUserId;

	@Id
	@Column(name = "MATCH_ID", nullable = false)
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Id
	@Column(name = "REFEREE_ID", nullable = false)
	public int getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(int refereeId) {
		this.refereeId = refereeId;
	}

	@Id
	@Column(name = "ADMIN_USER_ID", nullable = false)
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
		AdminUserRefereeMatch that = (AdminUserRefereeMatch) o;
		return matchId == that.matchId && refereeId == that.refereeId && adminUserId == that.adminUserId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchId, refereeId, adminUserId);
	}
}
