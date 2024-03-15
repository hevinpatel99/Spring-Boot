package com.jpa_audit.serviceimpl;


import com.jpa_audit.exception.CustomException;
import com.jpa_audit.model.CustomUser;
import com.jpa_audit.model.User;
import com.jpa_audit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailService extends User implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);
    private final UserRepository userRepository;


    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" UserName : {} ", username);
        Optional<User> user = userRepository.findByUserName(username);
        log.info(" User found status : {} ", user.isPresent());

        if (!user.isPresent()) {
            throw new CustomException("User not found !!!");
        }


        Set<GrantedAuthority> authorities = user.get().getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());

        log.info(" UserDetailService : {} || : {}  ", username, authorities);


        return new CustomUser(
                user.get().getId(),
                user.get().getUserName(),
                user.get().getPassword(),
                authorities
        );
    }


}
