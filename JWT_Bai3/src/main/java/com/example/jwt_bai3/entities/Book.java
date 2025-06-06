package com.example.jwt_bai3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min = 1, max = 255, message = "Tên cuốn sách không quá 255 kí tự.")
    String title;

    @NotNull
    @Size(min = 1, max = 255, message = "Tên tác giả không quá 255 kí tự.")
    String author;

    @NotNull
    @Min(value = 1,message = "Số lượng tối thiểu là 1.")
    @Max(value = 255, message = "Số lượng không vượt quá 255.")
    int quantity;
}
