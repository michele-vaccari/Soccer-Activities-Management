package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "REGISTERED_USER", schema = "SAM")
public class RegisteredUser {
	private int id;
	private int adminUserId;
	private String phone;
	private String address;
	private Referee refereeById;
	private User userById;

	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "ADMIN_USER_ID")
	public int getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	@Basic
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Basic
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegisteredUser that = (RegisteredUser) o;
		return id == that.id && adminUserId == that.adminUserId && Objects.equals(phone, that.phone) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, adminUserId, phone, address);
	}

	@OneToOne(mappedBy = "registeredUserById")
	public Referee getRefereeById() {
		return refereeById;
	}

	public void setRefereeById(Referee refereeById) {
		this.refereeById = refereeById;
	}

	@OneToOne
	@JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
	public User getUserById() {
		return userById;
	}

	public void setUserById(User userById) {
		this.userById = userById;
	}
}
