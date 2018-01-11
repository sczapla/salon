package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Permission;
import com.sczapla.salon.service.PermissionService;
import com.sczapla.salon.view.utils.DialogMode;
import com.sczapla.salon.view.utils.Utils;

@Component("permissionView")
@Scope("view")
public class PermissionView implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ResourceBundle messagesBundle;
	private List<Permission> permissions;
	private String dialogMode;
	private Permission newEntity;

	@Autowired
	private PermissionService permissionService;

	public PermissionView() {
		newEntity = new Permission();
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		permissions = permissionService.findAllOrderByNameAsc();
		dialogMode = DialogMode.ADD.name();
	}

	public void add() {
		newEntity = new Permission();
	}

	public void delete(Permission entity) {
		permissionService.delete(entity);
		Utils.addDetailMessage(messagesBundle.getString("info.delete"), FacesMessage.SEVERITY_INFO);
	}

	public void edit(Permission entity) {
		newEntity = entity;
	}

	public void save() {
		try {
			permissionService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
	}

	public String getDialogMode() {
		return dialogMode;
	}

	public void setDialogMode(String dialogMode) {
		this.dialogMode = dialogMode;
	}

	public Permission getNewEntity() {
		return newEntity;
	}

	public void setNewEntity(Permission newEntity) {
		this.newEntity = newEntity;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
