package com.sam.webapi.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOURNAMENT", schema = "SAM")
public class Tournament {
	private int id;
	private String type;
	private String name;
	private String description;
	private int adminUserId;

	public Tournament() { }

	public Tournament(int id,
					  String type,
					  String name,
					  String description,
					  int adminUserId) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
		this.adminUserId = adminUserId;
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
	@Column(name = "TYPE", columnDefinition="char(1)", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "ADMIN_USER_ID", nullable = false)
	public int getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tournament that = (Tournament) o;
		return id == that.id && Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, name, description);
	}
}
