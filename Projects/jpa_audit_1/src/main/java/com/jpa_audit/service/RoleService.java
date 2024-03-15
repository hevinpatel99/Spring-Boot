package com.jpa_audit.service;

import com.jpa_audit.dto.RoleDTO;
import com.jpa_audit.response.ApiResponse;
import com.jpa_audit.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {
    ResponseEntity<ApiResponse> insertRole(RoleDTO roleDTO, HttpServletRequest request);

    ResponseEntity<?> getAllRoles();
}
