package com.sczapla.salon.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	protected Long id;

	@Column(name = "version")
	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.getId() != null ? this.getId().hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;

		GenericEntity other = (GenericEntity) object;
		if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [ID=" + id + "]";
	}
}
