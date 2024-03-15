package com.jpa_projection.serviceImpl;

import com.jpa_projection.dto.UserEntityViewDto;
import com.jpa_projection.exception.CustomException;
import com.jpa_projection.model.Department;
import com.jpa_projection.model.UserDepartmentMapping;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.projection.UserProjection_close;
import com.jpa_projection.projection.UserProjection_open;
import com.jpa_projection.repository.DepartmentRepository;
import com.jpa_projection.repository.UserRepository;
import com.jpa_projection.responseDto.ApiResponseDto;
import com.jpa_projection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;


    @Override
    public ResponseEntity<ApiResponseDto> insertUser(UserDepartmentMapping user) {
        if (user != null) {
            Optional<UserEntity> userName = userRepository.findByUserName(user.getUserName());
            if (userName.isPresent())
                throw new CustomException("user already exists", HttpStatus.BAD_REQUEST);

            UserEntity userEntity = saveUser(user);
            Optional<Department> byId = departmentRepository.findById(user.getDepartmentId());
            if (byId.isPresent()) {
                Department department = byId.get();
                userEntity.setDepartment(department);
            }
            userEntity = userRepository.save(userEntity);
            System.out.println(user);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "user inserted successfully", userEntity));

        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Request", Collections.EMPTY_LIST));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getUser() {
        List<UserEntityViewDto> allUSer = userRepository.findAllUser();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "user fetched successfully", allUSer));

    }

    @Override
    public ResponseEntity<ApiResponseDto> getUserById(Long id) {
        UserEntityViewDto user = userRepository.findByUserId(id);
        System.out.println(user);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "user fetched successfully", user));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getUserByFirstName(String firstName) {
        if (!firstName.isEmpty()) {
            UserProjection_open userByFirstName = userRepository.findAllOpenProjectedByFirstName(firstName);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "user fetched successfully", userByFirstName));
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Request", Collections.EMPTY_LIST));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllUserByProjection() {
        List<UserProjection_close> allUSer = userRepository.findAllProjectedBy();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "user fetched successfully", allUSer));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getDynamicProjection() {
        List<UserProjection_close> allProjectedBy = userRepository.findAllProjectedBy(UserProjection_close.class);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "user fetched successfully", allProjectedBy));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getOpenProjection() {
        List<UserProjection_open> allUSer = userRepository.findAllOpenProjectedBy();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "user fetched successfully", allUSer));
    }


    private UserEntity saveUser(UserDepartmentMapping user) {
        return UserEntity.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .password(user.getPassword()).build();
    }

}
