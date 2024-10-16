package com.productapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagingService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private String redisChannel1;

    @Autowired
    private String redisChannel2;

    public void sendMessage1(String message){
        redisTemplate.convertAndSend(redisChannel1, message);
    }

    public void sendMessage2(String message){
        redisTemplate.convertAndSend(redisChannel2, message);
    }

}