package com.example.LearnAuthentication.service;

import com.example.LearnAuthentication.entity.RefreshToken;
import com.example.LearnAuthentication.entity.UserInfo;
import com.example.LearnAuthentication.repository.RefreshTokenRepository;
import com.example.LearnAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        // Assuming userRepository.findByUserId() returns a valid UserInfo object for the given userId
        UserInfo userInfo = userRepository.findByUsername(username);
        //System.out.println(userInfo.getId());
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with userId: " + username);
        }
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserInfoId(userInfo.getId());
        if (existingToken.isPresent()) {
            RefreshToken refreshToken = existingToken.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(60000000));  // Reset expiry date
            return refreshTokenRepository.save(refreshToken);
        } else {
            // If no existing token, create a new one
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .userInfo(userInfo)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(60000000))  // Set expiry date
                    .build();
            return refreshTokenRepository.save(newRefreshToken);
        }
        /*Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userInfo.getId());
        // or update it as needed
        existingToken.ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));


        /*
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000000))
                .build();
        return refreshTokenRepository.save(refreshToken);

         */
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}

