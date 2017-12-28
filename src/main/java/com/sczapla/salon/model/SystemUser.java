package com.sczapla.salon.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "system_user")
@AttributeOverride(name = "id", column = @Column(name = "system_user_id", nullable = false))
@SequenceGenerator(name = "default_gen", sequenceName = "system_user_seq", allocationSize = 1, initialValue = 100)
public class SystemUser extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "password_attempt_count", length = 2)
	private Integer passwordAttemptCount;

	@Column(name = "first_name", length = 64)
	private String firstName;

	@Column(name = "second_name", length = 64)
	private String secondName;

	@Column(name = "surname", length = 64)
	private String surname;

	@Column(name = "email_address", unique = true, nullable = false, length = 64)
	private String emailAddress;

	@Column(name = "phone_number", length = 64)
	private String phoneNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", length = 13)
	private Date birthDate;

	@Column(name = "pesel", unique = true, length = 11)
	private String peselNumber;

	@Column(name = "document_number", unique = true, length = 20)
	private String documentNumber;

	@Column(name = "registered")
	private Boolean registered;

	@Column(name = "description", length = 256)
	private String description;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", length = 64)
	private Date registrationDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "system_user_role", //
			joinColumns = { @JoinColumn(name = "system_user_id") }, //
			inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>(0);

	@Transient
	private Long attachCount;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPasswordAttemptCount() {
		return passwordAttemptCount;
	}

	public void setPasswordAttemptCount(Integer passwordAttemptCount) {
		this.passwordAttemptCount = passwordAttemptCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPeselNumber() {
		return peselNumber;
	}

	public void setPeselNumber(String peselNumber) {
		this.peselNumber = peselNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(Long attachCount) {
		this.attachCount = attachCount;
	}

}
