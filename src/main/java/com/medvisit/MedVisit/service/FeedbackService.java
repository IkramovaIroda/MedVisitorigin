package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    FeedbackDto create(FeedbackDto dto);
    FeedbackDto getById(Long id);
    List<FeedbackDto> getAll();
    List<FeedbackDto> getByClinic(Long clinicId);
    List<FeedbackDto> getByClient(Long clientId);
    void delete(Long id);
}
