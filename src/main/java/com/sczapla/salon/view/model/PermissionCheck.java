package com.sczapla.salon.view.model;

import com.sczapla.salon.model.Permission;

public class PermissionCheck {
	private Permission permission;
	private Boolean check;

	public PermissionCheck(Permission permission, Boolean check) {
		super();
		this.permission = permission;
		this.check = check;
	}

	public PermissionCheck() {
		super();
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

}
