package com.example.jwt_bai3.services;

import com.example.jwt_bai3.entities.Book;
import com.example.jwt_bai3.repositories.BookRepository;
import com.example.jwt_bai3.services.implement.BookServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices implements BookServicesImp {
    @Autowired
    BookRepository bookRepository;


    @Override
    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        return  books != null ? books : List.of();
    }

    @Override
    public Book postBook(Book book) {
        // Check if book exists
        if (bookRepository.findBooksByAuthorAndTitle(book.getAuthor(),book.getTitle()) != null) {
            throw new IllegalArgumentException("The book already exists.");
        }

        return bookRepository.save(book);
    }

    @Override
    public boolean updateBook(Book book) {
        // check book existence
        if(bookRepository.findById(book.getId()).isPresent()) {
            bookRepository.save(book);
            return true;
        }
        throw new IllegalArgumentException("The book does not exist.");
    }

    @Override
    public boolean deleteBook(Long id) {
        // check book existence
        if(bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        throw new IllegalArgumentException("The book does not exist.");
    }


}
