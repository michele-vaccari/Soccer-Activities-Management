package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
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
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HashMap<Integer, ArrayList<MatchDto>> firstRoundMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HashMap<Integer, ArrayList<MatchDto>> secondRoundMatches;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HashMap<Integer, ArrayList<MatchDto>>eighthFinalsMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HashMap<Integer, ArrayList<MatchDto>> quarterFinalsMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HashMap<Integer, ArrayList<MatchDto>> semifinalMatches;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private MatchDto finalMatch;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private List<RankingLineDto> ranking;

	public TournamentDto() { }

	public TournamentDto(int id,
						 String name,
						 String type,
						 String description,
						 HashMap<Integer, ArrayList<MatchDto>> firstRoundMatches,
						 HashMap<Integer, ArrayList<MatchDto>> secondRoundMatches,
						 List<RankingLineDto> ranking) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.firstRoundMatches = firstRoundMatches;
		this.secondRoundMatches = secondRoundMatches;
		this.ranking = ranking;
	}

	public TournamentDto(int id,
						 String name,
						 String type,
						 String description,
						 HashMap<Integer, ArrayList<MatchDto>> eighthFinalsMatches,
						 HashMap<Integer, ArrayList<MatchDto>> quarterFinalsMatches,
						 HashMap<Integer, ArrayList<MatchDto>>semifinalMatches,
						 MatchDto finalMatch,
						 List<RankingLineDto> ranking) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.eighthFinalsMatches = eighthFinalsMatches;
		this.quarterFinalsMatches = quarterFinalsMatches;
		this.semifinalMatches = semifinalMatches;
		this.finalMatch = finalMatch;
		this.ranking = ranking;
	}

	public TournamentDto(int id,
						 String name,
						 String type,
						 String description,
						 List<Integer> teamIds,
						 List<RankingLineDto> ranking) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.teamIds = teamIds;
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

	public HashMap<Integer, ArrayList<MatchDto>> getFirstRoundMatches() {
		return firstRoundMatches;
	}

	public void setFirstRoundMatches(HashMap<Integer, ArrayList<MatchDto>> firstRoundMatches) {
		this.firstRoundMatches = firstRoundMatches;
	}

	public HashMap<Integer, ArrayList<MatchDto>> getSecondRoundMatches() {
		return secondRoundMatches;
	}

	public void setSecondRoundMatches(HashMap<Integer, ArrayList<MatchDto>> secondRoundMatches) {
		this.secondRoundMatches = secondRoundMatches;
	}

	public HashMap<Integer, ArrayList<MatchDto>> getEighthFinalsMatches() {
		return eighthFinalsMatches;
	}

	public void setEighthFinalsMatches(HashMap<Integer, ArrayList<MatchDto>> eighthFinalsMatches) {
		this.eighthFinalsMatches = eighthFinalsMatches;
	}

	public HashMap<Integer, ArrayList<MatchDto>> getQuarterFinalsMatches() {
		return quarterFinalsMatches;
	}

	public void setQuarterFinalsMatches(HashMap<Integer, ArrayList<MatchDto>> quarterFinalsMatches) {
		this.quarterFinalsMatches = quarterFinalsMatches;
	}

	public HashMap<Integer, ArrayList<MatchDto>> getSemifinalMatches() {
		return semifinalMatches;
	}

	public void setSemifinalMatches(HashMap<Integer, ArrayList<MatchDto>> semifinalMatches) {
		this.semifinalMatches = semifinalMatches;
	}

	public MatchDto getFinalMatch() {
		return finalMatch;
	}

	public void setFinalMatch(MatchDto finalMatch) {
		this.finalMatch = finalMatch;
	}

	public List<Integer> getTeamIds() {
		return teamIds;
	}

	public void setTeamIds(List<Integer> teamIds) {
		this.teamIds = teamIds;
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
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(teamIds, that.teamIds) && Objects.equals(firstRoundMatches, that.firstRoundMatches) && Objects.equals(secondRoundMatches, that.secondRoundMatches) && Objects.equals(eighthFinalsMatches, that.eighthFinalsMatches) && Objects.equals(quarterFinalsMatches, that.quarterFinalsMatches) && Objects.equals(semifinalMatches, that.semifinalMatches) && Objects.equals(finalMatch, that.finalMatch) && Objects.equals(ranking, that.ranking);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type, description, teamIds, firstRoundMatches, secondRoundMatches, eighthFinalsMatches, quarterFinalsMatches, semifinalMatches, finalMatch, ranking);
	}
}
