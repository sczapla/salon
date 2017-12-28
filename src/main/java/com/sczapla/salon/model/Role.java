package com.sczapla.salon.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "role")
@AttributeOverride(name = "id", column = @Column(name = "role_id", nullable = false))
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1, initialValue = 100)
public class Role extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "name", length = 64)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<SystemUser> systemUser = new HashSet<>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_permission", //
			joinColumns = @JoinColumn(name = "role_id"), //
			inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SystemUser> getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Set<SystemUser> systemUser) {
		this.systemUser = systemUser;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Transient
	public String getPermissionsStr() {
		return permissions.stream().map(e -> e.getName()).collect(Collectors.joining(", "));
	}

}
