package com.jpa_audit.service;

import com.jpa_audit.dto.UserDto;
import com.jpa_audit.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> insertUser(UserDto userDto);

    ResponseEntity<?> getAllUser();

    ResponseEntity<?> updateUser(UserDto userDto, Long id);

    User getUserById(Long id);

    ResponseEntity<?> removeUser(Long id);
}
