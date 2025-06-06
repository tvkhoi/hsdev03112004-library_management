package com.example.jwt_bai3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min = 1, max = 255, message = "Tên độc giả không quá 255 kí tự.")
    String name;

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 500, message = "Email không quá 500 kí tự.")
    String email;
    
    @NotNull
    //@Size(min = 8, max = 20, message = "Mật khẩu tối thiểu 8 kí tự và tối đa 20 kí tự.")
//    @Pattern(
//        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
//        message = "Mật khẩu phải chứa ít nhất 1 kí tự viết hoa, 1 số, và 1 kí tự đặc biệt."
//    )
    String password;

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    String role = "User"; // "Admin" or "User"

    @OneToMany(mappedBy = "reader")
    List<BorrowRecord> borrowRecords;
}
