package com.example.LearnAuthentication.service;
import java.util.List;
import com.example.LearnAuthentication.dto.UserResponse;
import com.example.LearnAuthentication.dto.UserRequest;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();


}
