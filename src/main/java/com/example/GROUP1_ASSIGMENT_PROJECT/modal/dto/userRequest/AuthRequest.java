package com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.userRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    String email;
    String password;
}
