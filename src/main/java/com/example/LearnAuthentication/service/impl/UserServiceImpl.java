package com.example.LearnAuthentication.service.impl;

import com.example.LearnAuthentication.dto.UserRequest;
import com.example.LearnAuthentication.dto.UserResponse;
import com.example.LearnAuthentication.entity.UserInfo;
import com.example.LearnAuthentication.repository.UserRepository;
import com.example.LearnAuthentication.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
//        String usernameFromAccessToken = userDetail.getUsername();
//
//        UserInfo currentUser = userRepository.findByUsername(usernameFromAccessToken);

        // UserInfo savedUser = null;

        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // String rawPassword = userRequest.getPassword();
        // String encodedPassword = encoder.encode(rawPassword);

        
        UserInfo user = modelMapper.map(userRequest, UserInfo.class);
        // Using the shared PasswordEncoder bean keeps hashing strategy centralized and reusable.`r`n        
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        UserInfo savedUser;
        if (userRequest.getId() != null) {
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if (oldUser == null) {
                throw new NoSuchElementException("User not found for id: " + userRequest.getId());
            }
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setRoles(user.getRoles());
            savedUser = userRepository.save(oldUser);
        } else {
            if (userRepository.existsByUsername(userRequest.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + userRequest.getUsername());
            }
            savedUser = userRepository.save(user);
        }

        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        if (user == null) {
            throw new NoSuchElementException("User not found for username: " + usernameFromAccessToken);
        }
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponse>>() {
        }.getType();
        return modelMapper.map(users, setOfDTOsType);
    }
}

