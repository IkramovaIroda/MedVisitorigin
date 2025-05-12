package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.DocumentDto;
import com.medvisit.MedVisit.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public DocumentDto create(@RequestBody DocumentDto dto) {
        return documentService.create(dto);
    }

    @GetMapping("/{id}")
    public DocumentDto get(@PathVariable Long id) {
        return documentService.getById(id);
    }

    @GetMapping
    public List<DocumentDto> getAll() {
        return documentService.getAll();
    }

    @PutMapping("/{id}")
    public DocumentDto update(@PathVariable Long id, @RequestBody DocumentDto dto) {
        return documentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }
}
