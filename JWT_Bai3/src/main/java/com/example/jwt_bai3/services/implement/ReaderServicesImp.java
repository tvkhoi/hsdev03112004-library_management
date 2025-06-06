package com.example.jwt_bai3.services.implement;

import com.example.jwt_bai3.dto.AuthRequest;
import com.example.jwt_bai3.dto.ProfileResponse;
import com.example.jwt_bai3.entities.Reader;

public interface ReaderServicesImp {
    boolean register(Reader reader);
    String login(AuthRequest authRequest);
    ProfileResponse getProfile(String authHeader);
}
