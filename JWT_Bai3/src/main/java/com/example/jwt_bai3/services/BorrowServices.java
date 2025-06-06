package com.example.jwt_bai3.services;

import com.example.jwt_bai3.dto.BorrowRequest;
import com.example.jwt_bai3.entities.Book;
import com.example.jwt_bai3.entities.BorrowRecord;
import com.example.jwt_bai3.entities.Reader;
import com.example.jwt_bai3.repositories.BookRepository;
import com.example.jwt_bai3.repositories.BorrowRepository;
import com.example.jwt_bai3.repositories.ReaderRepository;
import com.example.jwt_bai3.securities.JwtUtil;
import com.example.jwt_bai3.services.implement.BorrowServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServices implements BorrowServicesImp {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Book borrowBook(BorrowRequest borrowRequest, String authHeader) {
        // User handling
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No valid token provided.");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.getEmailFromJwtToken(token);
        Reader reader = readerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Book processing
        Book book = bookRepository.findById(borrowRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + borrowRequest.getId()));
        // Check the number of books
        int requestedQuantity = borrowRequest.getQuantity();
        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (book.getQuantity() < requestedQuantity) {
            throw new IllegalArgumentException("Not enough books available. Available: " + book.getQuantity());
        }
        book.setQuantity(book.getQuantity() - borrowRequest.getQuantity());

        // Tạo BorrowRecord
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setReader(reader);
        borrowRecord.setBook(book);
        borrowRecord.setReturnDate(borrowRequest.getReturnDate());

        // Save data
        borrowRepository.save(borrowRecord);
        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(Long id) {
        // Check existing loan voucher
        if(!borrowRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }

        BorrowRecord borrowRecord = borrowRepository.findById(id).get();
        Book book = borrowRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        borrowRepository.deleteById(id);

        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBorrewedBook(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No valid token provided.");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.getEmailFromJwtToken(token);
        Reader reader = readerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        List<Book> books = new ArrayList<>();
        for(BorrowRecord borrowRecord : reader.getBorrowRecords()) {
            books.add(borrowRecord.getBook());
        }

        return books;
    }

}
