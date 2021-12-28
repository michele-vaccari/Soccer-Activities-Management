package com.sam.webapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "Users")
public class User {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	@Schema(description = "Type of users",
			allowableValues =  {"SystemAdministrator","TeamManager","Referee"})
	@NotEmpty
	private String type;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	@Schema(allowableValues =  {"Y", "N"})
	@NotEmpty
	private String isActive;

	@Id
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "Type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic
	@Column(name = "Name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "Surname", nullable = false)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Basic
	@Column(name = "Email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "Password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "IsActive", columnDefinition = "varchar(1)", nullable = false)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id && Objects.equals(type, user.type) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(isActive, user.isActive);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, name, surname, email, password, isActive);
	}
}
