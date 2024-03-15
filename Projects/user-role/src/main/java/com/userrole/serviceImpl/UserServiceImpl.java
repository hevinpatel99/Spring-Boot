package com.userrole.serviceImpl;


import com.userrole.entity.UserEntity;
import com.userrole.exception.CustomException;
import com.userrole.mappings.UserRoleMapping;
import com.userrole.repository.UserRepository;
import com.userrole.repository.UserRoleRepository;
import com.userrole.requestDto.UserRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.responseDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.userrole.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *  @author Hevin Mulani
 * Implementation of the UserService interface.
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;


    /**
     * Insert a new User.
     *
     * @param userRequestDto The Dto containing the name,userName & password to be inserted.
     * @return inserted new user details with HttpStatus.CREATED.
     */
    @Override
    public ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto) {

        //Check if user is existed or not.
        Optional<UserEntity> byUserName = userRepository.findByUserName(userRequestDto.getUserName());
        if (byUserName.isPresent()) {
            // throw a CustomException if user is present.
            throw new CustomException("user already exists", HttpStatus.BAD_REQUEST);
        }

        //save UserEntity
        UserEntity userEntity = saveUserEntity(userRequestDto);
        userEntity = this.userRepository.save(userEntity);

        //return inserted roleEntity with HttpStatus.CREATED.
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "User Inserted Successfully", userEntity));
    }


    /**
     * Retrieve all users with it's associated roles.
     *
     * @return list of userEntity with t's associated roles with HttpStatus.OK.
     */
    @Override
    public ResponseEntity<ApiResponseDto> getAllUser() {

        // retrieve all Users.
        List<UserEntity> allUser = userRepository.findAll();
        log.info("All USER : {} ", allUser);
        if (!allUser.isEmpty()) {
            List<UserResponseDto> userResponseDtoList = new ArrayList<>();

            // Iterate through each UserEntity
            for (UserEntity userEntity : allUser) {

                //return list of role entity with HttpStatus.OK.
                List<UserRoleMapping> byUser = userRoleRepository.findByUser(userEntity);

                // Map UserEntity to UserResponseDto and set associated roles
                UserResponseDto result = modelMapper.map(userEntity, UserResponseDto.class);
                result.setRoles(byUser.stream().map((p) -> p.getRole().getRoleName()).collect(Collectors.toSet()));
                userResponseDtoList.add(result);
            }
            //return list of userEntity with t's associated roles with HttpStatus.OK.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Get Successfully", userResponseDtoList));
        }

        // return empty list if users are not fetched.
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
    }

    /**
     * Save a new UserEntity.
     *
     * @param userRequestDto The Dto containing the name,userName & password to be inserted.
     * @return saved UserEntity object.
     */
    public UserEntity saveUserEntity(UserRequestDto userRequestDto) {
        // Create and return a new UserEntity with the name,userName & password set from the userRequestDto and status set a true by default.
        return UserEntity.builder().name(userRequestDto.getName())
                .userName(userRequestDto.getUserName())
                // Encode password
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .status(Boolean.TRUE)
                .build();
    }
}




