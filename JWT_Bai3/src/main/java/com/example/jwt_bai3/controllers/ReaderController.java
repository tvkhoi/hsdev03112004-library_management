package com.example.jwt_bai3.controllers;

import com.example.jwt_bai3.dto.AuthRequest;
import com.example.jwt_bai3.entities.Reader;
import com.example.jwt_bai3.entities.ResposeObject;
import com.example.jwt_bai3.services.ReaderServices;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/readers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderController {
    @Autowired
    ReaderServices readerServices;

    @PostMapping("/register")
    public ResponseEntity<ResposeObject> register(@RequestBody AuthRequest authRequest) {
        Reader reader = new Reader();
        reader.setName(authRequest.getName());
        reader.setEmail(authRequest.getEmail());
        reader.setPassword(authRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200", "Đăng kí tài khoản thành công.", readerServices.register(reader)));
    }

    @PostMapping("/login")
    public ResponseEntity<ResposeObject> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200", "Đăng nhập thành công.", readerServices.login(authRequest)));
    }

    @GetMapping("/me")
    public ResponseEntity<ResposeObject> getProfile(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200", "Lấy dữ liệu thành công.", readerServices.getProfile(authHeader)));
    }
}
