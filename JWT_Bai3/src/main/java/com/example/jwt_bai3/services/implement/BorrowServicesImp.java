package com.example.jwt_bai3.services.implement;

import com.example.jwt_bai3.dto.BorrowRequest;
import com.example.jwt_bai3.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BorrowServicesImp {
    Book borrowBook(BorrowRequest borrowRequest, String authHeader);
    Book returnBook(Long id);
    List<Book> getBorrewedBook(String authHeader);
}
