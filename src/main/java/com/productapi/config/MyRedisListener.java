package com.productapi.config;

import org.springframework.stereotype.Service;

@Service
public class MyRedisListener {

    public void onMessageChannel1(String message, String channel) {
        System.out.println("Received/Consumed message from channel1: " + message);
    }

    public void onMessageChannel2(String message, String channel) {
        System.out.println("Received/Consumed message from channel2: " + message);
    }
}

