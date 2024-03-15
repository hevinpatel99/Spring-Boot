package com.jpa_audit.controller;

import com.jpa_audit.dto.UserDto;
import com.jpa_audit.model.User;
import com.jpa_audit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PostMapping("/insertUser")
    public ResponseEntity<?> insertUser(@RequestBody @Valid UserDto userDto) {
        return userService.insertUser(userDto);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER')")
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser() {
        return userService.getAllUser();
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Long id) {
        return userService.updateUser(userDto, id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/changeStatus/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        return userService.removeUser(id);
    }


}
