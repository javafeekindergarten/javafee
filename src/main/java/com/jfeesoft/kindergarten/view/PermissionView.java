package com.jfeesoft.kindergarten.view;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jfeesoft.kindergarten.model.Permission;
import com.jfeesoft.kindergarten.service.PermissionService;

@Component("permissionView")
@Scope("view")
public class PermissionView extends GenericView<Permission> implements Serializable {

	private static final long serialVersionUID = 1L;

	public PermissionView(PermissionService genericService) {
		super(genericService);
	}

	@Override
	public void add() {
		newEntity = new Permission();
	}

}
