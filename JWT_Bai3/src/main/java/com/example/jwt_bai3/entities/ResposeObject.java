package com.example.jwt_bai3.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResposeObject {
    String status;
    String message;
    Object data;
}
