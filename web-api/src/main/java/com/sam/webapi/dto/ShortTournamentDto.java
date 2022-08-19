package com.sam.webapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class ShortTournamentDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	private String name;
	private String type;

	public ShortTournamentDto() { }

	public ShortTournamentDto(int id,
							  String name,
							  String type) {
		this.id = id;
		this.name = name;
		this.type = type;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ShortTournamentDto that = (ShortTournamentDto) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type);
	}
}
