package com.example.LearnAuthentication.service.impl;

import com.example.LearnAuthentication.entity.UserInfo;
import com.example.LearnAuthentication.repository.UserRepository;
import com.example.LearnAuthentication.service.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername called");
        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("Username not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
