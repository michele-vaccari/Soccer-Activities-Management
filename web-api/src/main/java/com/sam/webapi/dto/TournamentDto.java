package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class TournamentDto {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String type;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String description;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Integer> teamIds;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private List<MatchDto> matches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private List<RankingLineDto> ranking;

	public TournamentDto() { }

	public TournamentDto(int id,
						 String name,
						 String type,
						 String description,
						 List<MatchDto> matches,
						 List<RankingLineDto> ranking) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.matches = matches;
		this.ranking = ranking;
	}

	public TournamentDto(int id,
						 String name,
						 String type,
						 String description,
						 List<Integer> teamIds,
						 List<MatchDto> matches,
						 List<RankingLineDto> ranking) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.teamIds = teamIds;
		this.matches = matches;
		this.ranking = ranking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Integer> getTeamIds() {
		return teamIds;
	}

	public void setTeamIds(List<Integer> teamIds) {
		this.teamIds = teamIds;
	}

	public List<MatchDto> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchDto> matches) {
		this.matches = matches;
	}

	public List<RankingLineDto> getRanking() {
		return ranking;
	}

	public void setRanking(List<RankingLineDto> ranking) {
		this.ranking = ranking;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TournamentDto that = (TournamentDto) o;
		return id == that.id && name.equals(that.name) && type.equals(that.type) && description.equals(that.description) && Objects.equals(teamIds, that.teamIds) && Objects.equals(matches, that.matches) && Objects.equals(ranking, that.ranking);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type, description, teamIds, matches, ranking);
	}
}
