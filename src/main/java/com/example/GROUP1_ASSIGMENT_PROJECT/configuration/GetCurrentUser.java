package com.example.GROUP1_ASSIGMENT_PROJECT.configuration;


import com.example.GROUP1_ASSIGMENT_PROJECT.modal.entity.User;
import com.example.GROUP1_ASSIGMENT_PROJECT.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentUser {

    private final AppUserRepository appUserRepository;

    public GetCurrentUser(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return appUserRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + userDetails.getUsername()));
        }
        throw new RuntimeException("No authentication found");
    }
}

