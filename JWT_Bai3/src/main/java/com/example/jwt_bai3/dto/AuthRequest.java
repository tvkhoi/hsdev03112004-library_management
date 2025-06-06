package com.example.jwt_bai3.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AuthRequest {
    String name;
    String email;
    String password;
}
