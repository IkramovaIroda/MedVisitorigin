package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.VisitLogDto;
import com.medvisit.MedVisit.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit-logs")
@RequiredArgsConstructor
public class VisitLogController {

    private final VisitLogService visitLogService;

    @PostMapping
    public VisitLogDto create(@RequestBody VisitLogDto dto) {
        return visitLogService.create(dto);
    }

    @GetMapping("/{id}")
    public VisitLogDto get(@PathVariable Long id) {
        return visitLogService.getById(id);
    }

    @GetMapping
    public List<VisitLogDto> getAll() {
        return visitLogService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        visitLogService.delete(id);
    }
}
