package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class TeamManagerDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	private String name;
	private String surname;
	private String email;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private String active;
	private String phone;
	private String address;
	private String teamName;

	public TeamManagerDto() { }

	public TeamManagerDto(int id,
						  String name,
						  String surname,
						  String email,
						  String password,
						  String active,
						  String phone,
						  String address,
						  String teamName) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.active = active;
		this.phone = phone;
		this.address = address;
		this.teamName = teamName;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() { return active; }

	public void setActive(String active) { this.active = active; }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTeamName() { return teamName; }

	public void setTeamName(String teamName) { this.teamName = teamName; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeamManagerDto that = (TeamManagerDto) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(teamName, that.teamName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, surname, email, password, phone, address, teamName);
	}
}
