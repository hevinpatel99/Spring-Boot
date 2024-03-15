package com.jpa_audit.audit;

import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import com.jpa_audit.dto.ClaimsDto;
import com.jpa_audit.model.CustomUser;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    Logger log = LoggerFactory.getLogger(AuditorAwareImpl.class);

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClaimsDto user = (ClaimsDto) authentication.getPrincipal();
        if (!authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(user.getUserId());
    }
}