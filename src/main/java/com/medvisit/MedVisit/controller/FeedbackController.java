package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.FeedbackDto;
import com.medvisit.MedVisit.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public FeedbackDto create(@RequestBody FeedbackDto dto) {
        return feedbackService.create(dto);
    }

    @GetMapping("/{id}")
    public FeedbackDto getById(@PathVariable Long id) {
        return feedbackService.getById(id);
    }

    @GetMapping
    public List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }

    @GetMapping("/by-clinic/{clinicId}")
    public List<FeedbackDto> getByClinic(@PathVariable Long clinicId) {
        return feedbackService.getByClinic(clinicId);
    }

    @GetMapping("/by-client/{clientId}")
    public List<FeedbackDto> getByClient(@PathVariable Long clientId) {
        return feedbackService.getByClient(clientId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        feedbackService.delete(id);
    }
}
