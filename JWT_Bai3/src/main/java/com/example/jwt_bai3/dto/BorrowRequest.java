package com.example.jwt_bai3.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowRequest {
    Long id;

    @Max(value = 1, message = "Số lượng tối đa là 1.")
    int quantity;

    @NotNull
    @FutureOrPresent(message = "Ngày trả phải lơn hơn ngày hiện tại.")
    LocalDate returnDate;
}
