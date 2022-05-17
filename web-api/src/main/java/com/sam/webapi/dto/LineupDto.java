package com.sam.webapi.dto;

import java.util.List;
import java.util.Objects;

public class LineupDto {

	private int teamId;
	private List<Integer> mainPlayerIds;
	private List<Integer> reservePlayerIds;

	public LineupDto(int teamId, List<Integer> mainPlayerIds, List<Integer> reservePlayerIds) {
		this.teamId = teamId;
		this.mainPlayerIds = mainPlayerIds;
		this.reservePlayerIds = reservePlayerIds;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public List<Integer> getMainPlayerIds() {
		return mainPlayerIds;
	}

	public void setMainPlayerIds(List<Integer> mainPlayerIds) {
		this.mainPlayerIds = mainPlayerIds;
	}

	public List<Integer> getReservePlayerIds() {
		return reservePlayerIds;
	}

	public void setReservePlayerIds(List<Integer> reservePlayerIds) {
		this.reservePlayerIds = reservePlayerIds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LineupDto lineupDto = (LineupDto) o;
		return teamId == lineupDto.teamId && Objects.equals(mainPlayerIds, lineupDto.mainPlayerIds) && Objects.equals(reservePlayerIds, lineupDto.reservePlayerIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(teamId, mainPlayerIds, reservePlayerIds);
	}

}
