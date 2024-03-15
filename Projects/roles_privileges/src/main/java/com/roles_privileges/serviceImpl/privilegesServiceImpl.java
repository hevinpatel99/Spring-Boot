package com.roles_privileges.serviceImpl;

import com.roles_privileges.model.PrivilegeEntity;
import com.roles_privileges.repository.PrivilegesRepository;
import com.roles_privileges.requestDto.PrivilegeRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.PrivilegeResponseDto;
import com.roles_privileges.service.PrivilegesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class privilegesServiceImpl implements PrivilegesService {


    private final PrivilegesRepository privilegeRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponseDto> insertPrivilege(PrivilegeRequestDto privilegeRequestDto) {
        PrivilegeEntity privilegeEntity = savePrivilege(privilegeRequestDto);
        privilegeEntity = privilegeRepository.save(privilegeEntity);
        PrivilegeResponseDto result = modelMapper.map(privilegeEntity, PrivilegeResponseDto.class);
        if (result != null) {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Privileges Inserted Successfully", result));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Collections.EMPTY_LIST));
        }


    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllPrivilege() {
        List<PrivilegeEntity> getAllPrivilege = privilegeRepository.findAll();
        if (!getAllPrivilege.isEmpty()) {
            List<PrivilegeResponseDto> privilegeResponseDtoList = getAllPrivilege.stream().map(privilegeEntity -> modelMapper.map(privilegeEntity, PrivilegeResponseDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Privileges fetched Successfully", privilegeResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", Collections.EMPTY_LIST));
        }

    }

    public PrivilegeEntity savePrivilege(PrivilegeRequestDto privilegeRequestDto) {
        return PrivilegeEntity.builder().privilegeName(privilegeRequestDto.getPrivilegeName())
                .status(Boolean.TRUE)
                .build();
    }
}
