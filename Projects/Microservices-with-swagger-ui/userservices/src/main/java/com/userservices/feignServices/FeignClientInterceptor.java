//package com.userservices.feignServices;
//
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
//@Configuration
//@Component
//@RequiredArgsConstructor
//public class FeignClientInterceptor implements RequestInterceptor {
//
//
//    private final OAuth2AuthorizedClientManager manager;
//
//
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        String token = Objects.requireNonNull(manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build())).getAccessToken().getTokenValue();
//        requestTemplate.header("Authorization", "Bearer " + token);
//    }
//}
