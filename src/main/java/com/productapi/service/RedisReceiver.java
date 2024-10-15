package com.productapi.service;

import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {

    public void receiveMessage(String message){
        System.out.println("Got Message:: " + message);
    }

}