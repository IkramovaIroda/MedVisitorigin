package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.VisitDto;
import com.medvisit.MedVisit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public VisitDto create(@RequestBody VisitDto dto) {
        return visitService.createVisit(dto);
    }

    @GetMapping("/{id}")
    public VisitDto get(@PathVariable Long id) {
        return visitService.getVisitById(id);
    }

    @GetMapping
    public List<VisitDto> getAll() {
        return visitService.getAllVisits();
    }

    @PutMapping("/{id}")
    public VisitDto update(@PathVariable Long id, @RequestBody VisitDto dto) {
        return visitService.updateVisit(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        visitService.deleteVisit(id);
    }
}
