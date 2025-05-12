package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.DocumentTemplateDto;
import com.medvisit.MedVisit.service.DocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class DocumentTemplateController {

    private final DocumentTemplateService templateService;

    @PostMapping
    public DocumentTemplateDto create(@RequestBody DocumentTemplateDto dto) {
        return templateService.create(dto);
    }

    @GetMapping("/{id}")
    public DocumentTemplateDto getById(@PathVariable Long id) {
        return templateService.getById(id);
    }

    @GetMapping
    public List<DocumentTemplateDto> getAll() {
        return templateService.getAll();
    }

    @GetMapping("/by-clinic/{clinicId}")
    public List<DocumentTemplateDto> getByClinic(@PathVariable Long clinicId) {
        return templateService.getByClinic(clinicId);
    }

    @GetMapping("/active")
    public List<DocumentTemplateDto> getActiveTemplates() {
        return templateService.getActiveTemplates();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        templateService.delete(id);
    }
}
