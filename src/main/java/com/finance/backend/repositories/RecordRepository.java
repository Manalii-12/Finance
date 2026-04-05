package com.finance.backend.repositories;

import com.finance.backend.entities.Record;
import com.finance.backend.entities.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE " +
           "(:type IS NULL OR r.type = :type) AND " +
           "(:category IS NULL OR r.category = :category) AND " +
           "(cast(:startDate as date) IS NULL OR r.date >= :startDate) AND " +
           "(cast(:endDate as date) IS NULL OR r.date <= :endDate)")
    List<Record> findWithFilters(@Param("type") RecordType type,
                                 @Param("category") String category,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);
}
