package com.example.jwt_bai3.services.implement;

import com.example.jwt_bai3.entities.Book;

import java.util.List;

public interface BookServicesImp {
    List<Book> getBooks();
    Book postBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(Long id);
}
