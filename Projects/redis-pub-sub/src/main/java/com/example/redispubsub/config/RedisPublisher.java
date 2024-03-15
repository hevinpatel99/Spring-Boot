package com.example.redispubsub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;


/**
 * RedisPublisher class responsible for publishing messages to a Redis topic.
 */
@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final StringRedisTemplate redisTemplate;

    private final Topic topic;

    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
