package com.userrole.serviceImpl;

import com.userrole.entity.RoleEntity;
import com.userrole.exception.CustomException;
import com.userrole.repository.RoleRepository;
import com.userrole.requestDto.RoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Hevin Mulani
 * Implementation of the RoleService interface.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;


    /**
     * Insert a new role.
     *
     * @param roleRequestDto The DTO contains roleName to be inserted.
     * @return inserted new role details with HttpStatus.CREATED.
     */
    @Override
    public ResponseEntity<ApiResponseDto> insertRole(RoleRequestDto roleRequestDto) {

        log.info("RoleRequestDto : {} ", roleRequestDto.getRoleName());

        // retrieve roleEntity is find by the roleName
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByRoleNameIgnoreCase(roleRequestDto.getRoleName());

        // if role entity is present then throw a custom exception with proper message.
        if (optionalRoleEntity.isPresent()) {
            throw new CustomException("Role already exists", HttpStatus.BAD_REQUEST);
        }

        // save roleEntity.
        RoleEntity roleEntity = saveRoleEntity(roleRequestDto);
        roleEntity = this.roleRepository.save(roleEntity);

        //return inserted roleEntity with HttpStatus.CREATED.
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Roles Inserted Successfully", roleEntity));
    }


    /**
     * Retrieve all roles.
     *
     * @return list of roleEntity with HttpStatus.OK.
     */
    @Override
    public ResponseEntity<ApiResponseDto> getAllRoles() {
        // retrieve all roles.
        List<RoleEntity> getAllRoles = roleRepository.findAll();

        if (!getAllRoles.isEmpty()) {
            //return list of role entity with HttpStatus.OK.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Roles Fetched Successfully", getAllRoles));
        } else {
            // return empty list if roles are not fetched.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Collections.EMPTY_LIST));
        }
    }


    /**
     * Save a new RoleEntity.
     *
     * @param roleRequestDto The DTO contains roleName to be saved.
     * @return saved RoleEntity object.
     */
    public RoleEntity saveRoleEntity(RoleRequestDto roleRequestDto) {
        log.info("RoleRequestDto : {} ", roleRequestDto);

        // Create and return a new RoleEntity with the roleName set from the roleRequestDto and status set a true by default.
        return RoleEntity.builder().roleName(roleRequestDto.getRoleName())
                .status(Boolean.TRUE)
                .build();
    }

}
