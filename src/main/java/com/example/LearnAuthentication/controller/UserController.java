package com.example.LearnAuthentication.controller;

import com.example.LearnAuthentication.dto.AuthRequestDTO;
import com.example.LearnAuthentication.dto.JwtResponseDTO;
import com.example.LearnAuthentication.dto.RefreshTokenRequestDTO;
import com.example.LearnAuthentication.dto.UserRequest;
import com.example.LearnAuthentication.dto.UserResponse;
import com.example.LearnAuthentication.entity.RefreshToken;
import com.example.LearnAuthentication.service.JwtService;
import com.example.LearnAuthentication.service.RefreshTokenService;
import com.example.LearnAuthentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(
            JwtService jwtService,
            UserService userService,
            RefreshTokenService refreshTokenService,
            AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and issue tokens")
    @SecurityRequirements
    public JwtResponseDTO authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        }

        throw new UsernameNotFoundException("Invalid credentials");
    }

    // Example: @Validated enables constraint checks on method params like @Min on path variables.
    @GetMapping("/validate/id/{id}")
    public ResponseEntity<String> validateIdExample(@PathVariable @Min(value = 1, message = "id must be >= 1") Long id) {
        return ResponseEntity.ok("Valid id: " + id);
    }

    // Use hasRole('ADMIN') only when authorities are stored with ROLE_ prefix.
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        return "Welcome";
    }

    @GetMapping("/ping1")
    public String test1() {
        return "Welcome";
    }

    @PostMapping(value = "/save")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUser();
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        UserResponse userResponse = userService.getUser();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Issue a new access token using refresh token")
    @SecurityRequirements
    public JwtResponseDTO refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> JwtResponseDTO.builder()
                        .accessToken(jwtService.generateToken(userInfo.getUsername()))
                        .token(refreshTokenRequestDTO.getToken())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
    }
}
