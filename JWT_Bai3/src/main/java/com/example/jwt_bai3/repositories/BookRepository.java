package com.example.jwt_bai3.repositories;

import com.example.jwt_bai3.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBooksByAuthorAndTitle(String author, String title);
}
