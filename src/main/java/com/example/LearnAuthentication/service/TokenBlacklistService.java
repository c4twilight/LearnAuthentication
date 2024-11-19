package com.example.LearnAuthentication.service;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenBlacklistService {
    void addToBlacklist(HttpServletRequest request);
    boolean isBlacklisted(String token);
}
