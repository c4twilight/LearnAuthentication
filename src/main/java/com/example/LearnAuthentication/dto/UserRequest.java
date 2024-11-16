package com.example.LearnAuthentication.dto;

import com.example.LearnAuthentication.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private Set<Roles> roles;
}

