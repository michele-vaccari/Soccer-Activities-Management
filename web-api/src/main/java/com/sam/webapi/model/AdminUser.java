package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ADMIN_USER", schema = "SAM")
public class AdminUser {
	private int id;
	private User userById;

	public  AdminUser() { }

	public AdminUser(int id) {
		this.id = id;
	}

	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AdminUser adminUser = (AdminUser) o;
		return id == adminUser.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
