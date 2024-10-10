package com.example.GROUP1_ASSIGMENT_PROJECT.controller;

import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.response.ApiResponse;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.AppUserDto;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.UserRequest;
import com.example.GROUP1_ASSIGMENT_PROJECT.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @Operation(summary = "Get current user info")
    @GetMapping
    public ResponseEntity<?> viewUserDetails(){
        AppUserDto appUserDto = appUserService.viewUserDetails();
        ApiResponse<AppUserDto> apiResponse = ApiResponse.<AppUserDto>builder()
                .payload(appUserDto)
                .message("View user successfully.")
                .code(201)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(summary = "Update current user info")
    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserRequest userRequest) {
        AppUserDto updatedUser = appUserService.updateUser(userRequest);
        ApiResponse<AppUserDto> apiResponse = ApiResponse.<AppUserDto>builder()
                .payload(updatedUser)
                .message("User updated successfully.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}

