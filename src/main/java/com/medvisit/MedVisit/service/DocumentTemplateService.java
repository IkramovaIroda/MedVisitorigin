package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.DocumentTemplateDto;

import java.util.List;

public interface DocumentTemplateService {
    DocumentTemplateDto create(DocumentTemplateDto dto);
    DocumentTemplateDto getById(Long id);
    List<DocumentTemplateDto> getAll();
    List<DocumentTemplateDto> getByClinic(Long clinicId);
    List<DocumentTemplateDto> getActiveTemplates();
    void delete(Long id);
}
