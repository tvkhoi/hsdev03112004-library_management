package com.example.jwt_bai3.repositories;

import com.example.jwt_bai3.entities.Reader;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByEmail(@NotNull @Size(min = 1, max = 500, message = "Email không quá 500 kí tự.") String email);
}
