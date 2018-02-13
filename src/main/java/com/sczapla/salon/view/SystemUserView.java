package com.sczapla.salon.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Position;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DualListModel<Role> roles;

	private List<Role> roleSource;

	private String dialogMode;
	private SystemUser newEntity;
	private List<SystemUser> userSource;
	private Boolean employee;

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
		userSource = new ArrayList<SystemUser>();
		dialogMode = DialogMode.ADD.name();
		roleSource = (List<Role>) roleService.findAll();
		roles = new DualListModel<Role>(new ArrayList<Role>(), new ArrayList<Role>());
		initTable();
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
			newEntity.setRegistrationDate(new Date());
		}

		newEntity.setRoles(new HashSet<Role>(roles.getTarget()));
		if (employee == false) {
			newEntity.setPosition(null);
		}

		try {
			newEntity = (SystemUser) systemUserService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
			initTable();
		} catch (Exception e) {
			logger.error(e.getMessage());
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
	}

	public void delete(SystemUser entity) {
		systemUserService.delete(entity);
		Utils.addDetailMessage(messagesBundle.getString("info.delete"), FacesMessage.SEVERITY_INFO);
		initTable();
	}

	public void edit(SystemUser entity) {
		roles.getTarget().clear();
		roles.getSource().clear();
		roles.getTarget().addAll(entity.getRoles());
		roles.getSource().addAll(roleSource);
		roles.getSource().removeAll(entity.getRoles());
		if (entity.getPosition() != null) {
			employee = true;
		}
		newEntity = entity;
	}

	public void logout() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/appLogout");
	}

	private void initTable() {
		userSource.clear();
		userSource = (List<SystemUser>) systemUserService.findAll();
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

	public Position[] getPositions() {
		return Position.values();
	}

	public Boolean getEmployee() {
		return employee;
	}

	public void setEmployee(Boolean employee) {
		this.employee = employee;
	}

}
