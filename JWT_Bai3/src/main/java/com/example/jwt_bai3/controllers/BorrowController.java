package com.example.jwt_bai3.controllers;

import com.example.jwt_bai3.dto.BorrowRequest;
import com.example.jwt_bai3.entities.ResposeObject;
import com.example.jwt_bai3.services.BorrowServices;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/borrows")
@RolesAllowed({"User","Admin"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowController {
    @Autowired
    BorrowServices borrowServices;

    @PostMapping("/borrow")
    public ResponseEntity<ResposeObject> borrowBook(@Valid @RequestBody BorrowRequest borrowRequest, @RequestHeader("Authorization") String authHeader){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200", "Mượn thành công cuốn sách.", borrowServices.borrowBook(borrowRequest, authHeader)));
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<ResposeObject> returnBook(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200","Trả thành công cuốn sách.", borrowServices.returnBook(id)));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<ResposeObject> getMyBooks(@RequestHeader("Authorization") String authHeader){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200", "Danh sách các cuốn sách mượn", borrowServices.getBorrewedBook(authHeader)));
    }
}
