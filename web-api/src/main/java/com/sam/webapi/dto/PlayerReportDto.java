package com.sam.webapi.dto;

import java.util.Objects;

public class PlayerReportDto {
	int id;
	int jerseyNumber;
	String name;
	String surname;
	int goal;
	int admonitions;
	boolean ejection;

	public PlayerReportDto() { }

	public PlayerReportDto(int id,
						   int jerseyNumber,
						   String name,
						   String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jerseyNumber = jerseyNumber;
	}

	public PlayerReportDto(int id,
						   int jerseyNumber,
						   String name,
						   String surname,
						   int goal,
						   int admonitions,
						   boolean ejection) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jerseyNumber = jerseyNumber;
		this.goal = goal;
		this.admonitions = admonitions;
		this.ejection = ejection;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getAdmonitions() {
		return admonitions;
	}

	public void setAdmonitions(int admonitions) {
		this.admonitions = admonitions;
	}

	public boolean isEjection() {
		return ejection;
	}

	public void setEjection(boolean ejection) {
		this.ejection = ejection;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerReportDto that = (PlayerReportDto) o;
		return id == that.id && jerseyNumber == that.jerseyNumber && goal == that.goal && admonitions == that.admonitions && ejection == that.ejection && Objects.equals(name, that.name) && Objects.equals(surname, that.surname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, jerseyNumber, name, surname, goal, admonitions, ejection);
	}
}
