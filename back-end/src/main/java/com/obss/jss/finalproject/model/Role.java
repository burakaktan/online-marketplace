package com.obss.jss.finalproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Role extends BaseEntity
{
	@Enumerated(EnumType.STRING)
	@Column(length=20,unique=true,nullable=false)
	private RoleType name;

	public RoleType getName() {
		return name;
	}

	public void setName(RoleType name) {
		this.name = name;
	}
}
