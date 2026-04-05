package com.finance.backend.services;

import com.finance.backend.dtos.RecordDto;
import com.finance.backend.entities.Record;
import com.finance.backend.entities.User;
import com.finance.backend.exceptions.ResourceNotFoundException;
import com.finance.backend.repositories.RecordRepository;
import com.finance.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public RecordDto createRecord(RecordDto request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Record record = Record.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .notes(request.getNotes())
                .user(user)
                .build();

        Record savedRecord = recordRepository.save(record);
        return mapToDto(savedRecord);
    }

    public List<RecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public RecordDto getRecordById(Long id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        return mapToDto(record);
    }

    public RecordDto updateRecord(Long id, RecordDto request) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());

        Record updatedRecord = recordRepository.save(record);
        return mapToDto(updatedRecord);
    }

    public void deleteRecord(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Record not found");
        }
        recordRepository.deleteById(id);
    }

    private RecordDto mapToDto(Record record) {
        return RecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .type(record.getType())
                .category(record.getCategory())
                .date(record.getDate())
                .notes(record.getNotes())
                .build();
    }
}
