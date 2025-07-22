package com.crm.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class ProjectDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String propertyName;
	private String address;
	private String propertyArea;
	private long userId;
	private long createdOn;
	private long updatedOn;

	public ProjectDetails() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {

		this.propertyName = propertyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPropertyArea() {
		return propertyArea;
	}

	public void setPropertyArea(String propertyArea) {
		this.propertyArea = propertyArea;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

	@PrePersist
	protected void prePersistFunction() {
		this.createdOn = System.currentTimeMillis();
		this.updatedOn = System.currentTimeMillis();

	}

	public ProjectDetails(long id, String propertyName, String address, String propertyArea, long userId,
			long createdOn, long updatedOn) {
		super();
		this.id = id;
		this.propertyName = propertyName;
		this.address = address;
		this.propertyArea = propertyArea;
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "ProjectDetails [id=" + id + ", propertyName=" + propertyName + ", address=" + address
				+ ", propertyArea=" + propertyArea + ", userId=" + userId + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}

}
