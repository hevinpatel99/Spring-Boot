package com.roles_privileges.serviceImpl;

import com.google.gson.Gson;
import com.roles_privileges.exception.CustomException;
import com.roles_privileges.mappings.UserRoleMapping;
import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import com.roles_privileges.repository.RoleRepository;
import com.roles_privileges.repository.UserRepository;
import com.roles_privileges.repository.UserRoleRepository;
import com.roles_privileges.requestDto.UserRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.UserResponseDto;
import com.roles_privileges.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;


    @Override
    public ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto) {

        // Check if username exists or not.
        Optional<UserEntity> byUserName = userRepository.findByUserName(userRequestDto.getUserName());
        if (byUserName.isPresent()) {
            throw new CustomException("user already exists", HttpStatus.BAD_REQUEST);
        }

        //Save User Entity
        UserEntity userEntity = saveUserEntity(userRequestDto);
        this.userRepository.save(userEntity);

        //Find Role entity using role name
        RoleEntity roleEntity = this.roleRepository.findByRoleNameIgnoreCase(userRequestDto.getRoleName()).orElseThrow(() -> {
            return new CustomException("Role not found", HttpStatus.NOT_FOUND);
        });

        //Save User Role Entity.
        UserRoleMapping userRole = new UserRoleMapping();
        userRole.setRole(roleEntity);
        userRole.setUser(userEntity);
        this.userRoleRepository.save(userRole);


        UserResponseDto result = getUserResponseDto(userEntity);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "User Inserted Successfully", result));
    }


    @Override
    public ResponseEntity<ApiResponseDto> getAllUser() {
        List<UserEntity> allUser = userRepository.findAll();
        System.out.println("All USER : " + allUser);
        if (!allUser.isEmpty()) {
            List<UserResponseDto> userResponseDtoList = allUser.stream().map(this::getUserResponseDto).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Get Successfully", userResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserResponseDto result = getUserResponseDto(userEntity.get());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Get Successfully", result));
        }

        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateUser(UserRequestDto userRequestDto, Long id) {
        if (userRequestDto != null) {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isPresent()) {
                UserEntity getUserEntity = userEntity.get();
                getUserEntity.setName(userRequestDto.getName());
                getUserEntity.setUserName(userRequestDto.getUserName());
                getUserEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));




                if (!userRequestDto.getRoleName().isEmpty()) {
                    RoleEntity roleEntity = this.roleRepository.findByRoleNameIgnoreCase(userRequestDto.getRoleName()).orElseThrow(() -> {
                        return new CustomException("Role not found", HttpStatus.NOT_FOUND);
                    });

                    UserRoleMapping userRoleMapping = userRoleRepository.findByUser(userEntity.get());
                    userRoleMapping.setUser(getUserEntity);
                    userRoleMapping.setRole(roleEntity);
                    this.userRoleRepository.save(userRoleMapping);

                }

                UserResponseDto result = getUserResponseDto(getUserEntity);
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "User Updated Successfully", result));
            }
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "User Not updated", Collections.EMPTY_LIST));
    }

    @Override
    public ResponseEntity<ApiResponseDto> changeStatus(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserEntity user1 = user.get();
            if (user1.isStatus()) {
                user1.setStatus(Boolean.FALSE);
                userRepository.save(user1);
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User disabled Successfully", user1));
            } else {
                user1.setStatus(Boolean.TRUE);
                userRepository.save(user1);
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User enabled Successfully", user1));
            }
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "User Not found", null));
    }

    @Override
    public ResponseEntity<ApiResponseDto> removeUser(Long id) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isPresent()) {
                UserRoleMapping byUserRole = userRoleRepository.findByUser(userEntity.get());
                userRoleRepository.delete(byUserRole);
                userRepository.delete(userEntity.get());
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Removed Successfully", id));
        } catch (HttpClientErrorException.Unauthorized errorException) {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.UNAUTHORIZED, "Error: Unauthorized", id));
        }
    }

    private UserResponseDto getUserResponseDto(UserEntity userEntity) {
        UserRoleMapping byUserRole = userRoleRepository.findByUser(userEntity);
        UserResponseDto result = modelMapper.map(userEntity, UserResponseDto.class);
        result.setRoleName(byUserRole.getRole().getRoleName());
        return result;
    }

    public UserEntity saveUserEntity(UserRequestDto userRequestDto) {
        return UserEntity.builder().name(userRequestDto.getName())
                .userName(userRequestDto.getUserName())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .status(Boolean.TRUE)
                .build();
    }
}
