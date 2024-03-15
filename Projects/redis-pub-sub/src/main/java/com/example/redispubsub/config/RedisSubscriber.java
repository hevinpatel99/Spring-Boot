package com.example.redispubsub.config;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * RedisSubscriber listens for messages on configured Redis channels and processes them accordingly.
 */
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

//    public static List<String> messageList = new ArrayList<>();

    private final StringRedisTemplate redisTemplate;

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageContent = new String(message.getBody());
        if (channel.equals("acknowledgments")) {
            System.out.println(" ---- Received acknowledgment : " + messageContent + " ----- ");
        } else {
            // Handle the received message
            try {
                // Your message processing logic goes here
                System.out.println(" Received: " + message + " ::: " + new Date() + " :::: Thread : " + Thread.currentThread().getName());
                Thread.sleep(5000L);
                System.out.println(" ---> Sleep Over: " + message + " ::: " + new Date());

                // Send acknowledgment back to the publisher
                String acknowledgment = "ACK: " + channel + " : " + messageContent;
                redisTemplate.convertAndSend("acknowledgments", acknowledgment);
            } catch (Exception e) {
                System.out.println(" ---> Exception: " + e.getMessage() + " ::: " + new Date());
            }
        }
    }
}


