package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.DocumentDto;

import java.util.List;

public interface DocumentService {
    DocumentDto create(DocumentDto dto);
    DocumentDto getById(Long id);
    List<DocumentDto> getAll();
    DocumentDto update(Long id, DocumentDto dto);
    void delete(Long id);
}
