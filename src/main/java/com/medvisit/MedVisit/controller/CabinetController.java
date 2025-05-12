package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.CabinetDto;
import com.medvisit.MedVisit.service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;

    @PostMapping
    public CabinetDto create(@RequestBody CabinetDto dto) {
        return cabinetService.create(dto);
    }

    @GetMapping("/{id}")
    public CabinetDto getById(@PathVariable Long id) {
        return cabinetService.getById(id);
    }

    @GetMapping
    public List<CabinetDto> getAll() {
        return cabinetService.getAll();
    }

    @GetMapping("/by-clinic/{clinicId}")
    public List<CabinetDto> getByClinic(@PathVariable Long clinicId) {
        return cabinetService.getByClinic(clinicId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cabinetService.delete(id);
    }
}
