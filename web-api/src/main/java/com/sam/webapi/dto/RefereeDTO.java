package com.sam.webapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class RefereeDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	private String name;
	private String surname;
	private String email;
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	private String phone;
	private String address;
	private String birthDate;
	private String citizenship;
	private String resume;

	public RefereeDto() { }

	public RefereeDto(int id,
					  String name,
					  String surname,
					  String email,
					  String password,
					  String phone,
					  String address,
					  String birthDate,
					  String citizenship,
					  String resume) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.birthDate = birthDate;
		this.citizenship = citizenship;
		this.resume = resume;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

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
		RefereeDto that = (RefereeDto) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(birthDate, that.birthDate) && Objects.equals(citizenship, that.citizenship) && Objects.equals(resume, that.resume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, surname, email, password, phone, address, birthDate, citizenship, resume);
	}
}
