package com.finance.backend.services;

import com.finance.backend.dtos.DashboardSummaryDto;
import com.finance.backend.entities.Record;
import com.finance.backend.entities.RecordType;
import com.finance.backend.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final RecordRepository recordRepository;

    public DashboardSummaryDto getSummary() {
        List<Record> allRecords = recordRepository.findAll();

        BigDecimal totalIncome = allRecords.stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .map(Record::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = allRecords.stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .map(Record::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        Map<String, BigDecimal> categoryTotals = allRecords.stream()
                .collect(Collectors.groupingBy(
                        Record::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Record::getAmount, BigDecimal::add)
                ));

        return DashboardSummaryDto.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryTotals(categoryTotals)
                .build();
    }
}
