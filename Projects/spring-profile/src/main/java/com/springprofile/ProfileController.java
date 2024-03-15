package com.springprofile;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ProfileController {


    @Value("${spring.messages.basename}")
    private String message;


    @GetMapping("/profile")
    public String getActiveProfiles() {
        return message.toUpperCase();
    }
}
