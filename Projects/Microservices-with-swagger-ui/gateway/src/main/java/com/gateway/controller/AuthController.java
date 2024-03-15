package com.gateway.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/home")
    public String Message() {
        return "Api-gateway";
    }


//    @GetMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
//        System.out.println("User Email Id : " + user.getEmail());
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setUserId(user.getEmail());
//        authResponse.setAccessToken(client.getAccessToken().getTokenValue());
//        authResponse.setRefreshToken(Objects.requireNonNull(client.getRefreshToken()).getTokenValue());
//        authResponse.setExpiredAt(Objects.requireNonNull(client.getAccessToken().getExpiresAt()).getEpochSecond());
//        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//        authResponse.setAuthorities(authorities);
//        return ResponseEntity.ok(authResponse);
//    }

}
