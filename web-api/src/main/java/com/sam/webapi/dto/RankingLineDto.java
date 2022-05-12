package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class RankingLineDto {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int position;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int score;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int playedMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int wonMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int lostMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int tiedMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int goalsMade;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int goalsSuffered;

	public RankingLineDto() { }

	public RankingLineDto(int position,
						  int score,
						  int playedMatches,
						  int wonMatches,
						  int lostMatches,
						  int tiedMatches,
						  int goalsMade,
						  int goalsSuffered) {
		this.position = position;
		this.score = score;
		this.playedMatches = playedMatches;
		this.wonMatches = wonMatches;
		this.lostMatches = lostMatches;
		this.tiedMatches = tiedMatches;
		this.goalsMade = goalsMade;
		this.goalsSuffered = goalsSuffered;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPlayedMatches() {
		return playedMatches;
	}

	public void setPlayedMatches(int playedMatches) {
		this.playedMatches = playedMatches;
	}

	public int getWonMatches() {
		return wonMatches;
	}

	public void setWonMatches(int wonMatches) {
		this.wonMatches = wonMatches;
	}

	public int getLostMatches() {
		return lostMatches;
	}

	public void setLostMatches(int lostMatches) {
		this.lostMatches = lostMatches;
	}

	public int getTiedMatches() {
		return tiedMatches;
	}

	public void setTiedMatches(int tiedMatches) {
		this.tiedMatches = tiedMatches;
	}

	public int getGoalsMade() {
		return goalsMade;
	}

	public void setGoalsMade(int goalsMade) {
		this.goalsMade = goalsMade;
	}

	public int getGoalsSuffered() {
		return goalsSuffered;
	}

	public void setGoalsSuffered(int goalsSuffered) {
		this.goalsSuffered = goalsSuffered;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RankingLineDto that = (RankingLineDto) o;
		return position == that.position && score == that.score && playedMatches == that.playedMatches && wonMatches == that.wonMatches && lostMatches == that.lostMatches && tiedMatches == that.tiedMatches && goalsMade == that.goalsMade && goalsSuffered == that.goalsSuffered;
	}

	@Override
	public int hashCode() {
		return Objects.hash(position, score, playedMatches, wonMatches, lostMatches, tiedMatches, goalsMade, goalsSuffered);
	}
}
