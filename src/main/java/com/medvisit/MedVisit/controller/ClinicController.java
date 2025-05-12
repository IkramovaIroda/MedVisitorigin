package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ClinicDto;
import com.medvisit.MedVisit.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ClinicDto create(@RequestBody ClinicDto dto) {
        return clinicService.createClinic(dto);
    }

    @GetMapping("/{id}")
    public ClinicDto get(@PathVariable Long id) {
        return clinicService.getClinicById(id);
    }

    @GetMapping
    public List<ClinicDto> getAll() {
        return clinicService.getAllClinics();
    }

    @PutMapping("/{id}")
    public ClinicDto update(@PathVariable Long id, @RequestBody ClinicDto dto) {
        return clinicService.updateClinic(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clinicService.deleteClinic(id);
    }
}
