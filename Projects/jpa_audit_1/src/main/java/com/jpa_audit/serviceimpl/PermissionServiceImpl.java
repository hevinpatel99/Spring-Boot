/*
package com.jpa_audit.serviceimpl;


import com.jpa_audit.dto.PermissionDto;
import com.jpa_audit.model.Permission;
import com.jpa_audit.model.Role;
import com.jpa_audit.repository.PermissionRepository;
import com.jpa_audit.response.ApiResponse;
import com.jpa_audit.response.ErrorResponse;
import com.jpa_audit.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;


    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public ResponseEntity<?> insertPermission(PermissionDto permissionDto, HttpServletRequest request) {
        Permission permission = savePermission(permissionDto);
        permission = this.permissionRepository.save(permission);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.CREATED, permission, "Permission Inserted SuccessFully"));

    }

    private Permission savePermission(PermissionDto permissionDto) {
        return Permission.builder().permissionName(permissionDto.getPermissionName().toUpperCase()).build();
    }


    @Override
    public ResponseEntity<?> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        if (!permissions.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, permissions, "All permissions get successFully"));
        } else {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.NOT_FOUND, "Not Found"));
        }
    }
}
*/
