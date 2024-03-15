package com.example.redispubsub.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 *  RedisListenerConfig to Redis message listener container for acknowledgment messages.
 */
@RequiredArgsConstructor
@Configuration
public class RedisListenerConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisSubscriber redisSubscriber;

    @Bean
    public RedisMessageListenerContainer acknowledgmentMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisSubscriber, new ChannelTopic("acknowledgments"));
        return container;
    }
}
