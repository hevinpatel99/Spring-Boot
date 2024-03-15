package com.roles_privileges.serviceImpl;

import com.roles_privileges.exception.CustomException;
import com.roles_privileges.mappings.RolePrivilegeMapping;
import com.roles_privileges.mappings.UserRoleMapping;
import com.roles_privileges.model.PrivilegeEntity;
import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import com.roles_privileges.repository.PrivilegesRepository;
import com.roles_privileges.repository.RolePrivilegesRepository;
import com.roles_privileges.repository.RoleRepository;
import com.roles_privileges.requestDto.RoleRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.RoleResponseDto;
import com.roles_privileges.responseDto.UserResponseDto;
import com.roles_privileges.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegesRepository privilegesRepository;

    private final RolePrivilegesRepository rolePrivilegesRepository;

    private final ModelMapper modelMapper;


    @Override
    public ResponseEntity<ApiResponseDto> insertRole(RoleRequestDto roleRequestDto) {

        // Check if username exists or not.
        Optional<RoleEntity> byRoleName = roleRepository.findByRoleNameIgnoreCase(roleRequestDto.getRoleName());
        if (byRoleName.isPresent()) {
            throw new CustomException("Role already exists", HttpStatus.BAD_REQUEST);
        }

        //Save User Entity

        RoleEntity roleEntity = saveRoleEntity(roleRequestDto);
        roleEntity = this.roleRepository.save(roleEntity);

        Set<PrivilegeEntity> privileges = new HashSet<>();
        for (String privilege : roleRequestDto.getPrivilege()) {
            if (privilege != null && !privilege.isEmpty()) {
                PrivilegeEntity byPrivilegeName = this.privilegesRepository.findByPrivilegeName(privilege);
                privileges.add(byPrivilegeName);
            } else {
                PrivilegeEntity privilege1 = new PrivilegeEntity();
                privilege1.setPrivilegeName(privilege);
                PrivilegeEntity privilegeEntity = this.privilegesRepository.save(privilege1);
                privileges.add(privilegeEntity);
            }
        }

//        roleRequestDto.getPrivilege().forEach((privilege) -> {
//            PrivilegeEntity byPrivilegeName = (PrivilegeEntity) this.privilegesRepository.findByPrivilegeNameIgnoreCase(privilege);
//            if (byPrivilegeName != null) {
//                privileges.add(byPrivilegeName);
//            } else {
//                PrivilegeEntity privilege1 = new PrivilegeEntity();
//                privilege1.setPrivilegeName(privilege);
//                PrivilegeEntity save = (PrivilegeEntity) this.privilegesRepository.save(privilege1);
//                privileges.add(save);
//            }
//        });
//

        RoleEntity finalRoleEntity = roleEntity;
        privileges.forEach((privilege) -> {
            RolePrivilegeMapping privilegeRole = new RolePrivilegeMapping();
            privilegeRole.setRoleEntity(finalRoleEntity);
            privilegeRole.setPrivilegeEntity(privilege);
            this.rolePrivilegesRepository.save(privilegeRole);
        });

        List<RolePrivilegeMapping> privilegeRoleMapping = this.rolePrivilegesRepository.findByRoleEntity(roleEntity);
        RoleResponseDto result = this.modelMapper.map(roleEntity, RoleResponseDto.class);
        result.setPrivilege(privilegeRoleMapping.stream().map((p) -> p.getPrivilegeEntity().getPrivilegeName()).collect(Collectors.toSet()));
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Roles Inserted Successfully", result));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllRoles() {
        List<RoleEntity> getAllRoles = roleRepository.findAll();
        if (!getAllRoles.isEmpty()) {
            List<RoleResponseDto> roleRoleResponseDtoList = getAllRoles.stream().map(roleEntity -> {
                List<RolePrivilegeMapping> privilegeRoleMapping = this.rolePrivilegesRepository.findByRoleEntity(roleEntity);
                RoleResponseDto roleResponseDto = modelMapper.map(roleEntity, RoleResponseDto.class);
                roleResponseDto.setPrivilege(privilegeRoleMapping.stream().map((p) -> p.getPrivilegeEntity().getPrivilegeName()).collect(Collectors.toSet()));
                return roleResponseDto;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Roles Fetched Successfully", roleRoleResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Collections.EMPTY_LIST));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateRole(RoleRequestDto roleRequestDto, Long id) {
        if (roleRequestDto != null) {
            Optional<RoleEntity> roleEntity = roleRepository.findById(id);
            if (roleEntity.isPresent()) {
                RoleEntity getRoleEntity = roleEntity.get();
                System.out.println("Get Role Entity : " + getRoleEntity);
                if (!getRoleEntity.getRoleName().equalsIgnoreCase(roleRequestDto.getRoleName())) {
                    getRoleEntity.setRoleName(roleRequestDto.getRoleName());
                }

                Set<PrivilegeEntity> privileges = new HashSet<>();
//                List<RolePrivilegeMapping> privilegeRoleMapping = this.rolePrivilegesRepository.findByRoleEntity(getRoleEntity);


                PrivilegeEntity requestedPrivilegeEntity = new PrivilegeEntity();
                Set<String> privilege = roleRequestDto.getPrivilege();
                System.out.println("Requested Privilege : " + privilege);
                for (String requestedPrivilege : privilege) {
                    PrivilegeEntity privilegeEntity = privilegesRepository.findByPrivilegeName(requestedPrivilege);
                    requestedPrivilegeEntity.setPrivilegeName(privilegeEntity.getPrivilegeName());
                    privileges.add(requestedPrivilegeEntity);
                }
                System.out.println("Requested Privilege Entity" + requestedPrivilegeEntity);
                System.out.println("Privileges" + privileges);


                /*for (RolePrivilegeMapping rolePrivilegeMapping : privilegeRoleMapping) {
                    for (String requestedPrivilege : existedList) {
                        if (!rolePrivilegeMapping.getPrivilegeEntity().getPrivilegeName().contains(requestedPrivilege)) {
                            PrivilegeEntity byPrivilegeName = privilegesRepository.findByPrivilegeName(requestedPrivilege);
                            privileges.add(byPrivilegeName);
                        } else {
                            privileges.add(requestedPrivilege.)
                        }
                    }
                }

                System.out.println("Privileges : " + privileges);*/





               /* for (String s : newExistedList) {
                    privilegeEntity.setPrivilegeName(s);
                    for (RolePrivilegeMapping rolePrivilegeMapping : privilegeRoleMapping) {
                        rolePrivilegeMapping.setRoleEntity(getRoleEntity);
                        rolePrivilegeMapping.setPrivilegeEntity(privilegeEntity);
                        rolePrivilegesRepository.save(rolePrivilegeMapping);
                    }
                }) {
                    privilegeEntity.setPrivilegeName(s);
                    for (RolePrivilegeMapping rolePrivilegeMapping : privilegeRoleMapping) {
                        rolePrivilegeMapping.setRoleEntity(getRoleEntity);
                        rolePrivilegeMapping.setPrivilegeEntity(privilegeEntity);
                        rolePrivilegesRepository.save(rolePrivilegeMapping);
                    }
                }

                //Currently Privileges list
                List<String> existedPrivilegesList = privilegeRoleMapping.stream()
                        .map(privilegeMapping -> privilegeMapping.getPrivilegeEntity().getPrivilegeName()).collect(Collectors.toList());
                System.out.println("Database Privileges List : " + existedPrivilegesList);


                // To be saved
                List<String> newExistedList = existedList.stream().filter(s -> !(existedPrivilegesList.contains(s))).collect(Collectors.toList());
                System.out.println("newExistedList :: " + newExistedList);




                RoleResponseDto result = this.modelMapper.map(getRoleEntity, RoleResponseDto.class);
                result.setPrivilege(privilegeRoleMapping.stream().map((p) -> p.getPrivilegeEntity().getPrivilegeName()).collect(Collectors.toSet()));
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Roles Updated Successfully", result));*/
            }
        }
        return null;
    }


    public RoleEntity saveRoleEntity(RoleRequestDto roleRequestDto) {
        return RoleEntity.builder().roleName(roleRequestDto.getRoleName())
                .status(Boolean.TRUE)
                .build();
    }

}
