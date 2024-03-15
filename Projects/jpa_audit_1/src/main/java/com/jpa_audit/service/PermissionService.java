package com.jpa_audit.service;

import com.jpa_audit.dto.PermissionDto;
import com.jpa_audit.model.Permission;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PermissionService {
    ResponseEntity<?>  insertPermission(PermissionDto permissionDto, HttpServletRequest request);

    ResponseEntity<?> getAllPermissions();
}
