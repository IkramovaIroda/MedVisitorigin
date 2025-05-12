package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.FeedbackDto;
import com.medvisit.MedVisit.entity.*;
import com.medvisit.MedVisit.repository.*;
import com.medvisit.MedVisit.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ClientRepository clientRepository;
    private final ClinicRepository clinicRepository;
    private final VisitRepository visitRepository;

    @Override
    public FeedbackDto create(FeedbackDto dto) {
        Feedback feedback = new Feedback();
        copyDtoToEntity(dto, feedback);
        return toDto(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDto getById(Long id) {
        return feedbackRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    @Override
    public List<FeedbackDto> getAll() {
        return feedbackRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getByClinic(Long clinicId) {
        return feedbackRepository.findByClinicClinicId(clinicId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getByClient(Long clientId) {
        return feedbackRepository.findByClientClientId(clientId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }

    private void copyDtoToEntity(FeedbackDto dto, Feedback feedback) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        feedback.setClient(client);
        feedback.setClinic(clinic);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        feedback.setAnonymous(dto.isAnonymous());
        feedback.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now());

        if (dto.getVisitId() != null) {
            Visit visit = visitRepository.findById(dto.getVisitId())
                    .orElse(null);
            feedback.setVisit(visit);
        }
    }

    private FeedbackDto toDto(Feedback feedback) {
        return new FeedbackDto(
                feedback.getFeedbackId(),
                feedback.getClient().getClientId(),
                feedback.getClinic().getClinicId(),
                feedback.getVisit() != null ? feedback.getVisit().getVisitId() : null,
                feedback.getRating(),
                feedback.getComment(),
                feedback.getCreatedAt(),
                feedback.isAnonymous()
        );
    }
}
