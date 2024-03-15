package com.roles_privileges.audit;

import com.roles_privileges.dto.TokenClaimsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    Logger log = LoggerFactory.getLogger(AuditorAwareImpl.class);

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenClaimsDto user = (TokenClaimsDto) authentication.getPrincipal();
        if (!authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(user.getUserId());
//        return Optional.of(1L);
    }
}