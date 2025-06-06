package com.example.jwt_bai3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @NotNull
    LocalDate borrowDate = LocalDate.now();

    @NotNull
    @FutureOrPresent(message = "Ngày trả phải lơn hơn ngày hiện tại.")
    LocalDate returnDate;
}
