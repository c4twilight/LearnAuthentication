package com.example.LearnAuthentication.controller;

import com.example.LearnAuthentication.dto.AuthRequestDTO;
import com.example.LearnAuthentication.dto.JwtResponseDTO;
import com.example.LearnAuthentication.dto.UserRequest;
import com.example.LearnAuthentication.dto.UserResponse;
import com.example.LearnAuthentication.dto.RefreshTokenRequestDTO;
import com.example.LearnAuthentication.entity.RefreshToken;
import com.example.LearnAuthentication.service.JwtService;
import com.example.LearnAuthentication.service.RefreshTokenService;
import com.example.LearnAuthentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.cookieExpiry}")
    private long cookieExpiry;

//    @Operation(summary = "Authenticate User", description = "Authenticate the user and generate JWT tokens.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
//            @ApiResponse(responseCode = "403", description = "Invalid username or password"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
    /*@Operation(summary = "Login to the system using username and password",  description = "Authenticate the user and generate JWT tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully.",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class)))
            @ApiResponse(responseCode = "403", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(
            @Parameter(description = "User credentials for authentication") @RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request.");
        }
    }*/
    @Operation(summary = "Login to the system using username and password",  description = "Authenticate the user and generate JWT tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully.",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            // set accessToken to cookie header

            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry) //cookieExpiry = 3600; // Example: 1 hour (define this variable properly)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken()).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }

    @Operation(summary = "Ping Test for Admin", description = "Check if the application is running by admin")
    @ApiResponse(responseCode = "200", description = "Welcome message")
    @PreAuthorize("hasAuthority('ADMIN')")  //@PreAuthorize("hasRole('ADMIN')")  then we need to insert ROLE_ADMIN
    @GetMapping("/ping")
    public String test() {
        return "Welcome Admin";
    }

    @Operation(summary = "Ping Test", description = "Check if the application is running")
    @ApiResponse(responseCode = "200", description = "Welcome message")
    @GetMapping("/ping1")
    public String test1() {
        return "Welcome";
    }

    @Operation(summary = "Save User", description = "Create a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/save")
    public ResponseEntity<UserResponse> saveUser(
            @Parameter(description = "User details to create a new user")  @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Get All Users", description = "Retrieve all registered users.")
    @ApiResponse(responseCode = "200", description = "List of users")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUser();
        return ResponseEntity.ok(userResponses);
    }

    @Operation(summary = "Get User Profile", description = "Retrieve the profile of the logged-in user.")
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully")
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        UserResponse userResponse = userService.getUser();
        return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Refresh Token", description = "Generate a new access token using the refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New access token generated"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(
            @Parameter(description = "Refresh token details to generate new access token") @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB."));
    }

    @Operation(summary = "logout to the system ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully."),
            @ApiResponse(responseCode = "403", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Clear any session-related data if necessary
        return ResponseEntity.ok(userService.logout(request));
    }

}