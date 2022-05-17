package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MATCH", schema = "SAM")
public class Match {
	private int id;
	private String type;
	private String place;
	private String date;
	private String time;
	private Report reportsById;

	public Match() {}

	public Match(int id,
				 String type,
				 String place,
				 String date,
				 String time) {
		this.id = id;
		this.type = type;
		this.place = place;
		this.date = date;
		this.time = time;
	}

	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic
	@Column(name = "PLACE")
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Basic
	@Column(name = "DATE")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Basic
	@Column(name = "TIME")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Match match = (Match) o;
		return id == match.id && Objects.equals(type, match.type) && Objects.equals(place, match.place) && Objects.equals(date, match.date) && Objects.equals(time, match.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, place, date, time);
	}

	@OneToOne(mappedBy = "matchByMatchId")
	public Report getReportsById() {
		return reportsById;
	}

	public void setReportsById(Report reportsById) {
		this.reportsById = reportsById;
	}
}
