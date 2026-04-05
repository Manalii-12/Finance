package com.finance.backend.repositories;

import com.finance.backend.entities.Record;
import com.finance.backend.entities.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Record> findByType(RecordType type);
    List<Record> findByCategory(String category);
}
