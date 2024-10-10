package com.example.GROUP1_ASSIGMENT_PROJECT.service;

import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.AppUserDto;
import com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserDto register(UserRequest request);
    AppUserDto findUserByEmail(String email);
    AppUserDto findUserByusername(String username);
    AppUserDto viewUserDetails();
    AppUserDto updateUser(UserRequest UserRequest);
}
