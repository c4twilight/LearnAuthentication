package com.example.LearnAuthentication.dto;

import com.example.LearnAuthentication.entity.Roles;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
    private Set<Roles> roles;
}

