package com.crm.user;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.annotation.Nullable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String role;
	private String name;
	private String email;
	private String mobile;
	@Enumerated(EnumType.STRING)
	@Column(name = "action", length = 100)
	private Status action;
	private String password;
	@Nullable
	private String otp;
	@Nullable
	private LocalDateTime otpCreationTime;
	private long createdOn;
	private long updatedOn;
	private String profilePic;
//	private long userId;
	@ElementCollection
	@CollectionTable(name = "client_sales_ids", joinColumns = @JoinColumn(name = "client_id"))
	@Column(name = "sales_id")
	private List<Long> salesId;

	@ElementCollection
	@CollectionTable(name = "client_crm_ids", joinColumns = @JoinColumn(name = "client_id"))
	@Column(name = "crm_id")
	private List<Long> crmIds;

	public long getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public Status getAction() {
		return action;
	}

	public String getPassword() {
		return password;
	}

	public String getOtp() {
		return otp;
	}

	public LocalDateTime getOtpCreationTime() {
		return otpCreationTime;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public List<Long> getCrmIds() {
		return crmIds;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAction(Status action) {
		this.action = action;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public void setOtpCreationTime(LocalDateTime otpCreationTime) {
		this.otpCreationTime = otpCreationTime;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public void setCrmIds(List<Long> crmIds) {
		this.crmIds = crmIds;
	}

	@PrePersist
	protected void prePersistFunction() {
		this.createdOn = System.currentTimeMillis();
	}

	@PostPersist
	protected void postPersistFunction() {
		this.updatedOn = System.currentTimeMillis();
	}

	public Client() {
	}

	public List<Long> getSalesId() {
		return salesId;
	}

	public void setSalesId(List<Long> salesId) {
		this.salesId = salesId;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", " + (role != null ? "role=" + role + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (action != null ? "action=" + action + ", " : "")
				+ (password != null ? "password=" + password + ", " : "") + (otp != null ? "otp=" + otp + ", " : "")
				+ (otpCreationTime != null ? "otpCreationTime=" + otpCreationTime + ", " : "") + "createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", "
				+ (profilePic != null ? "profilePic=" + profilePic + ", " : "")
				+ (salesId != null ? "salesId=" + salesId + ", " : "") + (crmIds != null ? "crmIds=" + crmIds : "")
				+ "]";
	}

	public Client(long id, String role, String name, String email, String mobile, Status action, String password,
			String otp, LocalDateTime otpCreationTime, long createdOn, long updatedOn, String profilePic,
			List<Long> salesId, List<Long> crmIds) {
		super();
		this.id = id;
		this.role = role;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.action = action;
		this.password = password;
		this.otp = otp;
		this.otpCreationTime = otpCreationTime;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.profilePic = profilePic;
		this.salesId = salesId;
		this.crmIds = crmIds;
	}

}
