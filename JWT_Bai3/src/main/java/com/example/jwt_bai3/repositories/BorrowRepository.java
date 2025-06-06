package com.example.jwt_bai3.repositories;

import com.example.jwt_bai3.entities.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord,Long> {
}
