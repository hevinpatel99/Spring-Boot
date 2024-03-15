package com.jpa_audit.serviceimpl;

import com.jpa_audit.dto.RoleDTO;
import com.jpa_audit.model.Role;
import com.jpa_audit.repository.RoleRepository;
import com.jpa_audit.response.ApiResponse;
import com.jpa_audit.response.ErrorResponse;
import com.jpa_audit.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> insertRole(RoleDTO roleDTO, HttpServletRequest request) {
        Role role = saveRole(roleDTO);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.CREATED, this.roleRepository.save(role), "Role Inserted SuccessFully"));

    }

    @Override
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (!roles.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, roles, "All Roles get successFully"));
        } else {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.NOT_FOUND, "Not Found"));
        }
    }

    public Role saveRole(RoleDTO roleDTO) {
        return Role.builder().roleName(roleDTO.getRoleName().toUpperCase()).build();
    }
}
