package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class RankingLineDto {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer position;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String teamName;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer score;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer playedMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer wonMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer lostMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tiedMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer goalsMade;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer goalsSuffered;

	public RankingLineDto() { }

	public RankingLineDto(Integer position,
						  String teamName,
						  Integer playedMatches,
						  Integer wonMatches,
						  Integer lostMatches,
						  Integer goalsMade,
						  Integer goalsSuffered) {
		this.position = position;
		this.teamName = teamName;
		this.playedMatches = playedMatches;
		this.wonMatches = wonMatches;
		this.lostMatches = lostMatches;
		this.goalsMade = goalsMade;
		this.goalsSuffered = goalsSuffered;
	}

	public RankingLineDto(Integer position,
						  String teamName,
						  Integer score,
						  Integer playedMatches,
						  Integer wonMatches,
						  Integer lostMatches,
						  Integer tiedMatches,
						  Integer goalsMade,
						  Integer goalsSuffered) {
		this.position = position;
		this.teamName = teamName;
		this.score = score;
		this.playedMatches = playedMatches;
		this.wonMatches = wonMatches;
		this.lostMatches = lostMatches;
		this.tiedMatches = tiedMatches;
		this.goalsMade = goalsMade;
		this.goalsSuffered = goalsSuffered;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getPlayedMatches() {
		return playedMatches;
	}

	public void setPlayedMatches(Integer playedMatches) {
		this.playedMatches = playedMatches;
	}

	public Integer getWonMatches() {
		return wonMatches;
	}

	public void setWonMatches(Integer wonMatches) {
		this.wonMatches = wonMatches;
	}

	public Integer getLostMatches() {
		return lostMatches;
	}

	public void setLostMatches(Integer lostMatches) {
		this.lostMatches = lostMatches;
	}

	public Integer getTiedMatches() {
		return tiedMatches;
	}

	public void setTiedMatches(Integer tiedMatches) {
		this.tiedMatches = tiedMatches;
	}

	public Integer getGoalsMade() {
		return goalsMade;
	}

	public void setGoalsMade(Integer goalsMade) {
		this.goalsMade = goalsMade;
	}

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
		RankingLineDto that = (RankingLineDto) o;
		return Objects.equals(position, that.position) && Objects.equals(teamName, that.teamName) && Objects.equals(score, that.score) && Objects.equals(playedMatches, that.playedMatches) && Objects.equals(wonMatches, that.wonMatches) && Objects.equals(lostMatches, that.lostMatches) && Objects.equals(tiedMatches, that.tiedMatches) && Objects.equals(goalsMade, that.goalsMade) && Objects.equals(goalsSuffered, that.goalsSuffered);
	}

	@Override
	public int hashCode() {
		return Objects.hash(position, teamName, score, playedMatches, wonMatches, lostMatches, tiedMatches, goalsMade, goalsSuffered);
	}
}
