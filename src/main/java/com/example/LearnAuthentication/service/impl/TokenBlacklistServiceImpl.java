package com.example.LearnAuthentication.service.impl;

import com.example.LearnAuthentication.service.JwtService;
import com.example.LearnAuthentication.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistServiceImpl  implements TokenBlacklistService {
    //InMemoryTokenBlacklist

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistServiceImpl.class);
    //private final Set<String> blacklist = new HashSet<>();
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtService jwtService;



    @Override
    public void addToBlacklist(HttpServletRequest request) {
        //blacklist.add(token);
        String token = jwtService.extractTokenFromRequest(request);
        Date expiry = jwtService.extractExpiration(token);
        long expiration = expiry.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isBlacklisted(String token) {
        //return blacklist.contains(token);
        boolean blackListed =  Boolean.TRUE.equals(redisTemplate.hasKey(token));
        if(blackListed){
            logger.info("Token is blacklisted.");
        }
        return blackListed;
    }
}
