package com.productapi.controller;

import com.productapi.dto.UserDto;
import com.productapi.service.RedisMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@Controller
@RequestMapping("/redis")
public class RedisController {

    Logger logger = LoggerFactory.getLogger("RedisController.class");

    @Autowired
    private RedisMessagingService redisMessagingService;

    // http://localhost:9000/redis/pubsub
    @GetMapping("/pubsub")
    public String pubSub(@RequestParam(required=false) String message, @RequestParam(required=false) Integer channelNum){
        if(message != null){
            logger.debug("##########pubSub() publishing message:: " + message );
            if(channelNum == 1)
                redisMessagingService.sendMessage1(message); // use channel 1
            else if(channelNum == 2)
                redisMessagingService.sendMessage2(message); // use channel 2
            else
                redisMessagingService.sendMessage1(message); // default, without channel info
        }
        else{
            logger.debug("##########pubSub()........." );
        }
        return "redis-pubsub";
    } // end of pubSub()
}