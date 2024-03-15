package com.example.redispubsub.controller;


import com.example.redispubsub.config.RedisPublisher;
import com.example.redispubsub.config.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublisherController {

    private final RedisPublisher redisPublisher;

    @PostMapping("/publish")
    public String publishMessage() {

        long startTime = System.currentTimeMillis();
        String message = "Message";
        for (int i = 1; i <= 100; i++) {
            try{
                String msg = message + " " + i;
                redisPublisher.publish(msg);
            } catch (Exception e) {
                System.out.println(" ---> Exception: " +e.getMessage()+" ::: "+ new Date());
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("elapsedTime = " + elapsedTime);
        return "Messages published successfully in Start Time : " +startTime+ " End time : "+ endTime+ " Difference " + elapsedTime + " milliseconds";
    }
}
