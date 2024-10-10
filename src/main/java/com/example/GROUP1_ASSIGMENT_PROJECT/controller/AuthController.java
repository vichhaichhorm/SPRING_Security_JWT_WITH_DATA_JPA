package com.example.GROUP1_ASSIGMENT_PROJECT.controller;

import com.example.GROUP1_ASSIGMENT_PROJECT.exception.CustomNotfoundException;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.response.ApiResponse;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.AppUserDto;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.AuthRequest;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.UserRequest;
import com.example.GROUP1_ASSIGMENT_PROJECT.security.JwtService;
import com.example.GROUP1_ASSIGMENT_PROJECT.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AuthController(AppUserService appUserService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.appUserService = appUserService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    @Operation(summary = "register as a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
        AppUserDto appUserDto = appUserService.register(userRequest);

        ApiResponse<AppUserDto> apiResponse = ApiResponse.<AppUserDto>builder()
                .payload(appUserDto)
                .message("Register successfully.")
                .code(201)
                .status(HttpStatus.CREATED)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Login with credentials")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        AppUserDto appUser = appUserService.findUserByEmail(authRequest.getEmail().toLowerCase());
        authenticate(appUser.getUsername().toLowerCase(), authRequest.getPassword());
        final UserDetails userDetails = appUserService.loadUserByUsername(appUser.getUsername().toLowerCase());
        final String jwt = jwtService.generateToken(userDetails);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .payload(Map.of("token", jwt))
                .message("You have logged in to the system successfully.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = appUserService.loadUserByUsername(username);
            if (userApp == null) {
                throw new CustomNotfoundException("Wrong Email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())) {
                throw new CustomNotfoundException("Wrong Password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}

