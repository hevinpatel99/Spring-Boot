package com.jpa_audit.serviceimpl;

import com.google.gson.Gson;
import com.jpa_audit.dto.UserDto;
import com.jpa_audit.exception.CustomException;
import com.jpa_audit.model.Role;
import com.jpa_audit.model.User;
import com.jpa_audit.repository.RoleRepository;
import com.jpa_audit.repository.UserRepository;
import com.jpa_audit.response.ApiResponse;
import com.jpa_audit.response.ErrorResponse;
import com.jpa_audit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> insertUser(UserDto userDto) {

        Optional<User> userName = userRepository.findByUserName(userDto.getUserName());
        if (userName.isPresent()) {
            throw new CustomException("userName is already exists!!");
        }

        if (userDto.getRoleName() != null && !userDto.getRoleName().isEmpty()) {
            Optional<Role> role = roleRepository.findByRoleName(userDto.getRoleName());
            if (role.isPresent()) {
                Role roles = role.get();
                userDto.setRoles(new HashSet<>(Collections.singletonList(roles)));
            }
        } else {
            Optional<Role> role = roleRepository.findByRoleName("USER");
            if (role.isPresent()) {
                Role roles = role.get();
                userDto.setRoles(new HashSet<>(Collections.singletonList(roles)));
            }
        }


        User user = saveUser(userDto);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.CREATED, this.userRepository.save(user), "User Inserted SuccessFully"));
    }

    @Override
    public ResponseEntity<?> getAllUser() {

        List<User> userList = this.userRepository.findAll();
        log.info(" UserList {0} : " + userList.size());
        if (!userList.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, userList, "User List Get SuccessFully"));
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "User List Not Found"));
    }

    @Override
    public ResponseEntity<?> updateUser(UserDto userDto, Long id) {
        if (userDto != null) {
            User user = getUserById(id);
            log.info("Update User : {0} " + new Gson().toJson(user));

            if (user != null) {
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setUserName(userDto.getUserName());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                if (userDto.getRoleName() != null && !userDto.getRoleName().isEmpty()) {
                    Optional<Role> role = roleRepository.findByRoleName(userDto.getRoleName());
                    if (role.isPresent()) {
                        Role roles = role.get();
                        user.setRoles(new HashSet<>(Collections.singletonList(roles)));
                    }
                }
                user = this.userRepository.save(user);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, user, "User updated Successfully"));
            }
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "User Not updated"));
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        log.info("User : {0} " + new Gson().toJson(user));
        return user.orElse(null);
    }

    @Override
    public ResponseEntity<?> removeUser(Long id) {
        Optional<User> user = Optional.ofNullable(getUserById(id));
        if (user.isPresent()) {
            User user1 = user.get();
            log.info(" Get User status : {}  ", user1.isStatus());
            if (user1.isStatus()) {
                user1.setStatus(Boolean.FALSE);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, user1, "User disabled Successfully"));
            } else {
                user1.setStatus(Boolean.TRUE);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, user1, "User enabled Successfully"));
            }
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "User Not found"));
    }


    public User saveUser(UserDto userDto) {
        return User.builder().firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userName(userDto.getUserName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .status(Boolean.TRUE)
                .roles(userDto.getRoles())
                .build();
    }
}
