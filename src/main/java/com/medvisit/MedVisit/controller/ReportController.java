package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ReportDto;
import com.medvisit.MedVisit.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ReportDto create(@RequestBody ReportDto dto) {
        return reportService.create(dto);
    }

    @GetMapping("/{id}")
    public ReportDto getById(@PathVariable Long id) {
        return reportService.getById(id);
    }

    @GetMapping
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/by-clinic/{clinicId}")
    public List<ReportDto> getByClinic(@PathVariable Long clinicId) {
        return reportService.getByClinic(clinicId);
    }

    @GetMapping("/archived/{archived}")
    public List<ReportDto> getArchived(@PathVariable boolean archived) {
        return reportService.getArchived(archived);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reportService.delete(id);
    }
}
