package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.MedicalQuestionnaireDto;
import com.medvisit.MedVisit.service.MedicalQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questionnaires")
@RequiredArgsConstructor
public class MedicalQuestionnaireController {

    private final MedicalQuestionnaireService questionnaireService;

    @PostMapping
    public MedicalQuestionnaireDto create(@RequestBody MedicalQuestionnaireDto dto) {
        return questionnaireService.create(dto);
    }

    @PutMapping("/{id}")
    public MedicalQuestionnaireDto update(@PathVariable Long id, @RequestBody MedicalQuestionnaireDto dto) {
        return questionnaireService.update(id, dto);
    }

    @GetMapping("/{id}")
    public MedicalQuestionnaireDto get(@PathVariable Long id) {
        return questionnaireService.getById(id);
    }

    @GetMapping("/client/{clientId}")
    public MedicalQuestionnaireDto getByClient(@PathVariable Long clientId) {
        return questionnaireService.getByClientId(clientId);
    }

    @GetMapping
    public List<MedicalQuestionnaireDto> getAll() {
        return questionnaireService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionnaireService.delete(id);
    }
}
