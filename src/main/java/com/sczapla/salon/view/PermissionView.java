package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
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
	private TreeNode root;
	private HashMap<Long, Permission> permissionRoot;
	private Long idPermission;
	private TreeNode[] selectedNodes;
	private String dialogMode;
	private Permission newEntity;

	@Autowired
	private PermissionService permissionService;

	public PermissionView() {
		newEntity = new Permission();
		newEntity.setComponent("asdf");
		newEntity.setName("sd");
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		permissionRoot = new HashMap<Long, Permission>();
		initPermissionTree();
		dialogMode = DialogMode.ADD.name();
	}

	private void initPermissionTree() {
		List<Permission> permissionRootAll = (List<Permission>) permissionService.findAllRootPermission();
		root = new CheckboxTreeNode(new Permission(), null);
		for (Permission permission : permissionRootAll) {
			createPermissionTree(root, permission);
		}
		permissionRoot.clear();
		List<Permission> permissionAll = (List<Permission>) permissionService.findAllOrderByNameAsc();

		for (Permission permission : permissionAll) {
			permissionRoot.put(permission.getId(), permission);
		}
	}

	private void createPermissionTree(TreeNode root, Permission permission) {
		CheckboxTreeNode node = new CheckboxTreeNode(permission, root);
		node.setExpanded(true);
		if (permission.getChildren() != null) {
			for (Permission permissionChild : permission.getChildren()) {
				createPermissionTree(node, permissionChild);
			}
		}
	}

	public void permissionEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Document Edited", ((TreeNode) event.getObject()).toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void permissionEditCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((TreeNode) event.getObject()).toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void add() {
		newEntity = new Permission();
	}

	public void delete(Permission entity) {
		permissionService.delete(entity);
		Utils.addDetailMessage(messagesBundle.getString("info.delete"), FacesMessage.SEVERITY_INFO);
		initPermissionTree();
	}

	public void edit(Permission entity) {
		newEntity = entity;
		idPermission = null;
		if (entity.getParent() != null) {
			idPermission = entity.getParent().getId();
		}
	}

	public void save() {
		Permission permissionTempRoot = null;
		if (idPermission != null) {
			permissionTempRoot = permissionRoot.get(idPermission);
			newEntity.setParent(permissionTempRoot);
		} else {
			newEntity.setParent(null);
		}
		try {
			permissionService.save(newEntity);
			Utils.addDetailMessage(messagesBundle.getString("info.edit"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			Utils.addDetailMessage(messagesBundle.getString("message.error.undefinedSaveException"),
					FacesMessage.SEVERITY_ERROR);
		}
		initPermissionTree();
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Collection<Permission> getPermissionRootList() {
		return permissionRoot.values();
	}

	public Long getIdPermission() {
		return idPermission;
	}

	public void setIdPermission(Long idPermission) {
		this.idPermission = idPermission;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
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

}
