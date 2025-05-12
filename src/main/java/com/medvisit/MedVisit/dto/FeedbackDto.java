package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private Long feedbackId;
    private Long clientId;
    private Long clinicId;
    private Long visitId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private boolean anonymous;
}
