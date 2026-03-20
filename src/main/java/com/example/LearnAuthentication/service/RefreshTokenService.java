package com.example.LearnAuthentication.service;

import com.example.LearnAuthentication.entity.RefreshToken;
import com.example.LearnAuthentication.entity.UserInfo;
import com.example.LearnAuthentication.repository.RefreshTokenRepository;
import com.example.LearnAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final long refreshTokenExpirationMs;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository,
            // 604800000 ms = 7 days refresh token lifetime by default.
            @Value("${security.jwt.refresh-token-expiration-ms:604800000}") long refreshTokenExpirationMs
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public RefreshToken createRefreshToken(String username) {
        UserInfo userInfo = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        // Keep a single refresh-token row per user and rotate token value on each login.
        RefreshToken refreshToken = refreshTokenRepository.findByUserInfo(userInfo)
                .orElse(RefreshToken.builder().userInfo(userInfo).build());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new IllegalArgumentException(token.getToken() + " refresh token expired. Please login again.");
        }
        return token;
    }
}
