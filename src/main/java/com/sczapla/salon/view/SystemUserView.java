package com.sczapla.salon.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Role;
import com.sczapla.salon.model.SystemUser;
import com.sczapla.salon.service.RoleService;
import com.sczapla.salon.service.SystemUserService;
import com.sczapla.salon.view.utils.DialogMode;
import com.sczapla.salon.view.utils.Utils;

@Component("systemUserView")
@Scope("view")
public class SystemUserView implements Serializable {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle messagesBundle;

	private DualListModel<Role> roles;

	private List<Role> roleSource;

	private String dialogMode;
	private SystemUser newEntity;
	private List<SystemUser> userSource;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public SystemUserView(SystemUserService genericService) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
		newEntity = new SystemUser();
	}

	@PostConstruct
	public void init() {
		dialogMode = DialogMode.ADD.name();
		roleSource = (List<Role>) roleService.findAll();
		roles = new DualListModel<Role>(new ArrayList<Role>(), new ArrayList<Role>());
		userSource = (List<SystemUser>) systemUserService.findAll();
	}

	public void add() {
		newEntity = new SystemUser();
		roles.getTarget().clear();
		roles.getSource().clear();
		roles.getSource().addAll(roleSource);
	}

	public void save() {
		if (dialogMode.equals(DialogMode.ADD.name())) {
			newEntity.setPassword(bcryptEncoder.encode(newEntity.getPassword()));
			newEntity.setIsActive(true);
		}

		newEntity.setRoles(new HashSet<Role>(roles.getTarget()));

		try {
			newEntity = (SystemUser) systemUserService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
	}

	public void edit(SystemUser entity) {
		roles.getTarget().clear();
		roles.getSource().clear();
		roles.getTarget().addAll(entity.getRoles());
		roles.getSource().addAll(roleSource);
		roles.getSource().removeAll(entity.getRoles());
		newEntity = entity;
	}

	public void logout() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/appLogout");
	}

	public DualListModel<Role> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<Role> roles) {
		this.roles = roles;
	}

	public String getDialogMode() {
		return dialogMode;
	}

	public void setDialogMode(String dialogMode) {
		this.dialogMode = dialogMode;
	}

	public List<Role> getRoleSource() {
		return roleSource;
	}

	public void setRoleSource(List<Role> roleSource) {
		this.roleSource = roleSource;
	}

	public List<SystemUser> getUserSource() {
		return userSource;
	}

	public void setUserSource(List<SystemUser> userSource) {
		this.userSource = userSource;
	}

	public SystemUser getNewEntity() {
		return newEntity;
	}

	public void setNewEntity(SystemUser newEntity) {
		this.newEntity = newEntity;
	}

}
