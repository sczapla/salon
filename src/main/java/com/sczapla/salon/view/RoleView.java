package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Permission;
import com.sczapla.salon.model.Role;
import com.sczapla.salon.service.PermissionService;
import com.sczapla.salon.service.RoleService;
import com.sczapla.salon.view.model.PermissionCheck;
import com.sczapla.salon.view.utils.DialogMode;
import com.sczapla.salon.view.utils.Utils;

@Component("roleView")
@Scope("view")
public class RoleView implements Serializable {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle messagesBundle;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	private List<PermissionCheck> permissions;
	private List<Permission> permissionAll;

	private List<Role> roleSource;
	private Long idRoleParent;
	private Role selectedRole;
	private String dialogMode;
	private Role newEntity;

	public RoleView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		newEntity = new Role();
		dialogMode = DialogMode.ADD.name();
		permissions = new ArrayList<PermissionCheck>();
		roleSource = (List<Role>) roleService.findAll();
		permissionAll = permissionService.findAllOrderByNameAsc();
		if (roleSource.size() > 0) {
			selectedRole = roleSource.get(0);
		}
		checkPermission(selectedRole.getPermissions());
	}

	public void add() {
		newEntity = new Role();
	}

	public void edit(Role entity) {
		newEntity = entity;
	}

	public void save() {
		newEntity.getPermissions().clear();
		if (idRoleParent != null) {
			// TODO
			// Role parentRole =
			// genericLazyModel.getRowData(idRoleParent.toString());
			// newEntity.setPermissions(parentRole.getPermissions());
		}
		try {
			newEntity = roleService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
	}

	public void savePermission() {
		if (selectedRole != null) {
			selectedRole.getPermissions().clear();
			// collectCheckedPermission(selectedRole.getPermissions());
			try {
				newEntity = roleService.save(selectedRole);
				Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
						FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	public void onRowSelect(SelectEvent event) {
		Role role = (Role) event.getObject();
		checkPermission(role.getPermissions());
	}

	private void collectCheckedPermission(Set<Permission> permissions, TreeNode rootNode) {
		for (TreeNode node : rootNode.getChildren()) {
			if (((PermissionCheck) node.getData()).getCheck()) {
				permissions.add(((PermissionCheck) node.getData()).getPermission());
			}
			if (!node.isLeaf()) {
				collectCheckedPermission(permissions, node);
			}

		}
	}

	private void checkPermission(Set<Permission> permissionRole) {
		permissions.clear();
		for (Permission permission : permissionAll) {
			PermissionCheck permCheck = new PermissionCheck(permission, permissionRole.contains(permission));
			permissions.add(permCheck);
		}
	}

	public List<Role> getRoleSource() {
		return roleSource;
	}

	public void setRoleSource(List<Role> roleSource) {
		this.roleSource = roleSource;
	}

	public Long getIdRoleParent() {
		return idRoleParent;
	}

	public void setIdRoleParent(Long idRoleParent) {
		this.idRoleParent = idRoleParent;
	}

	public List<PermissionCheck> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionCheck> permissions) {
		this.permissions = permissions;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public String getDialogMode() {
		return dialogMode;
	}

	public void setDialogMode(String dialogMode) {
		this.dialogMode = dialogMode;
	}

	public Role getNewEntity() {
		return newEntity;
	}

	public void setNewEntity(Role newEntity) {
		this.newEntity = newEntity;
	}

}
