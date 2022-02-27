package com.sam.webapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "SAM")
public class User {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	@Schema(description = "Role of users",
			allowableValues = {"Admin","TeamManager","Referee"})
	@NotEmpty
	private String role;
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
	private String active;
	private RegisteredUser registeredUserById;

	public User() { }

	public User(int id,
				String role,
				String name,
				String surname,
				String email,
				String password,
				String active) {
		this.id = id;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.active = active;
	}

	@Id
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "ROLE", nullable = false)
	public String getRole() {
		return role;
	}

	public void setRole(String type) {
		this.role = type;
	}

	@Basic
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "SURNAME", nullable = false)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Basic
	@Column(name = "EMAIL", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "ACTIVE", columnDefinition = "varchar(1)", nullable = false)
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id && Objects.equals(role, user.role) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(active, user.active);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, name, surname, email, password, active);
	}

	@OneToOne(mappedBy = "userById")
	public RegisteredUser getRegisteredUserById() {
		return registeredUserById;
	}

	public void setRegisteredUserById(RegisteredUser registeredUserById) {
		this.registeredUserById = registeredUserById;
	}
}
