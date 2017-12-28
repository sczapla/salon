package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.CheckboxTreeNode;
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

	private TreeNode rootPermission;

	private List<Role> roleSource;
	private Long idRoleParent;
	private Role selectedRole;
	private TreeNode[] selectedPermission;
	private String dialogMode;
	private Role newEntity;

	public RoleView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		newEntity = new Role();
		dialogMode = DialogMode.ADD.name();
		roleSource = (List<Role>) roleService.findAll();
		List<Permission> permissionRootAll = permissionService.findAllRootPermission();
		if (roleSource.size() > 0) {
			selectedRole = roleSource.get(0);
		}
		rootPermission = new CheckboxTreeNode(new PermissionCheck(), null);
		for (Permission permission : permissionRootAll) {
			createPermissionTree(rootPermission, permission);
		}
		checkPermission(selectedRole.getPermissions(), rootPermission);
	}

	private void createPermissionTree(TreeNode root, Permission permission) {
		CheckboxTreeNode node = new CheckboxTreeNode(new PermissionCheck(permission, false), root);
		node.setExpanded(true);
		if (permission.getChildren() != null) {
			for (Permission permissionChild : permission.getChildren()) {
				createPermissionTree(node, permissionChild);
			}
		}
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
			newEntity = (Role) roleService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public void savePermission() {
		if (selectedRole != null) {
			selectedRole.getPermissions().clear();
			collectCheckedPermission(selectedRole.getPermissions(), rootPermission);
			try {
				newEntity = (Role) roleService.save(selectedRole);
				Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
			} catch (Exception e) {
				Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
						FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	public void onRowSelect(SelectEvent event) {
		Role role = (Role) event.getObject();
		checkPermission(role.getPermissions(), rootPermission);
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

	private void checkPermission(Set<Permission> permissions, TreeNode rootNode) {
		for (TreeNode node : rootNode.getChildren()) {
			PermissionCheck permissionNode = (PermissionCheck) node.getData();
			boolean selected = permissions.contains(permissionNode.getPermission());
			// node.setSelected(selected);
			permissionNode.setCheck(selected);
			if (!node.isLeaf()) {// && !selected) {
				checkPermission(permissions, node);
			}
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

	public TreeNode getRootPermission() {
		return rootPermission;
	}

	public void setRootPermission(TreeNode root) {
		this.rootPermission = root;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public TreeNode[] getSelectedPermission() {
		return selectedPermission;
	}

	public void setSelectedPermission(TreeNode[] selectedPermission) {
		this.selectedPermission = selectedPermission;
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
