package com.sczapla.salon.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "visit")
@AttributeOverride(name = "id", column = @Column(name = "visit_id", nullable = false))
@SequenceGenerator(name = "default_gen", sequenceName = "visit_seq", allocationSize = 1, initialValue = 100)
public class Visit extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "comments", length = 256)
	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "visit_from", nullable = false)
	private Date visitFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "visit_to", nullable = false)
	private Date visitTo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "system_user_from_id")
	private SystemUser userFrom;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "system_user_to_id")
	private SystemUser userTo;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getVisitFrom() {
		return visitFrom;
	}

	public void setVisitFrom(Date visitFrom) {
		this.visitFrom = visitFrom;
	}

	public Date getVisitTo() {
		return visitTo;
	}

	public void setVisitTo(Date visitTo) {
		this.visitTo = visitTo;
	}

	public SystemUser getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(SystemUser userFrom) {
		this.userFrom = userFrom;
	}

	public SystemUser getUserTo() {
		return userTo;
	}

	public void setUserTo(SystemUser userTo) {
		this.userTo = userTo;
	}

}
