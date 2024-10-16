package com.productapi.config;

import com.productapi.service.RedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter1,
                                                   MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter1, new PatternTopic(redisChannel1()));
        container.addMessageListener(listenerAdapter2, new PatternTopic(redisChannel2()));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter1(MyRedisListener listener) {
        return new MessageListenerAdapter(listener, "onMessageChannel1");
    }

    @Bean
    public MessageListenerAdapter listenerAdapter2(MyRedisListener listener) {
        return new MessageListenerAdapter(listener, "onMessageChannel2");
    }

//    @Bean
//    public MessageListenerAdapter listerAdapter(RedisReceiver receiver){
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public String redisChannel2(){
        return "CRUD-Template-Redis-PubSub-Queue2";
    }

    @Bean
    public String redisChannel1(){
        return "CRUD-Template-Redis-PubSub-Queue1";
    }

}