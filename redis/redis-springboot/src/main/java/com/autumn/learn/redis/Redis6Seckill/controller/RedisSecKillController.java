package com.autumn.learn.redis.Redis6Seckill.controller;

import com.autumn.learn.redis.Redis6Seckill.config.SecKill_redisByScript;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/redis")
public class RedisSecKillController {

    @RequestMapping("/doseckill")
    public boolean secKill(@RequestParam("prodid") String prodId) throws IOException {
        String userid = new Random().nextInt(50000) + "";

        //boolean isSuccess=SecKill_redis.doSecKill(userid,prodid);
        boolean isSuccess = SecKill_redisByScript.doSecKill(userid, prodId);
        return isSuccess;
    }


}
