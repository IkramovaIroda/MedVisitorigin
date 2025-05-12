package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.DocumentTemplateDto;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.entity.DocumentTemplate;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.repository.DocumentTemplateRepository;
import com.medvisit.MedVisit.service.DocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    private final DocumentTemplateRepository documentTemplateRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public DocumentTemplateDto create(DocumentTemplateDto dto) {
        DocumentTemplate template = new DocumentTemplate();
        template.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
        template.setTitle(dto.getTitle());
        template.setContent(dto.getContent());
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        template.setIsActive(dto.getIsActive());
        return toDto(documentTemplateRepository.save(template));
    }

    @Override
    public DocumentTemplateDto getById(Long id) {
        return documentTemplateRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    @Override
    public List<DocumentTemplateDto> getAll() {
        return documentTemplateRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentTemplateDto> getByClinic(Long clinicId) {
        return documentTemplateRepository.findByClinicClinicId(clinicId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentTemplateDto> getActiveTemplates() {
        return documentTemplateRepository.findByIsActiveTrue().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        documentTemplateRepository.deleteById(id);
    }

    private DocumentTemplateDto toDto(DocumentTemplate t) {
        return new DocumentTemplateDto(
                t.getTemplateId(),
                t.getClinic().getClinicId(),
                t.getTitle(),
                t.getContent(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getIsActive()
        );
    }
}
