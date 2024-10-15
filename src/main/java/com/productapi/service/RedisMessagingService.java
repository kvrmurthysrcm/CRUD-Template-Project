package com.productapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagingService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private String redisChannel;

    public void sendMessage(String message){
        redisTemplate.convertAndSend(redisChannel, message);
    }
}
