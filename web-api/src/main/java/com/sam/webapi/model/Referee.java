package com.sam.webapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "REFEREE", schema = "SAM")
public class Referee {
	private int id;
	private String birthDate;
	private String citizenship;
	private String resume;
	private RegisteredUser registeredUserById;
	private List<Report> reportsById;

	public Referee() { }

	public Referee(int id, String birthDate, String citizenship, String resume) {
		this.id = id;
		this.birthDate = birthDate;
		this.citizenship = citizenship;
		this.resume = resume;
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
	@Column(name = "BIRTH_DATE")
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@Basic
	@Column(name = "CITIZENSHIP")
	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Basic
	@Column(name = "RESUME")
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Referee referee = (Referee) o;
		return id == referee.id && Objects.equals(birthDate, referee.birthDate) && Objects.equals(citizenship, referee.citizenship) && Objects.equals(resume, referee.resume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, birthDate, citizenship, resume);
	}

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
	public RegisteredUser getRegisteredUserById() {
		return registeredUserById;
	}

	public void setRegisteredUserById(RegisteredUser registeredUserById) {
		this.registeredUserById = registeredUserById;
	}

	@OneToMany(mappedBy = "refereeByRefereeId")
	public List<Report> getReportsById() {
		return reportsById;
	}

	public void setReportsById(List<Report> reportsById) {
		this.reportsById = reportsById;
	}
}
