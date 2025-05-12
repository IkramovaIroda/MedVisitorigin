package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ServiceDto;
import com.medvisit.MedVisit.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ServiceDto create(@RequestBody ServiceDto dto) {
        return serviceService.create(dto);
    }

    @GetMapping("/{id}")
    public ServiceDto get(@PathVariable Long id) {
        return serviceService.getById(id);
    }

    @GetMapping
    public List<ServiceDto> getAll() {
        return serviceService.getAll();
    }

    @PutMapping("/{id}")
    public ServiceDto update(@PathVariable Long id, @RequestBody ServiceDto dto) {
        return serviceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        serviceService.delete(id);
    }
}
