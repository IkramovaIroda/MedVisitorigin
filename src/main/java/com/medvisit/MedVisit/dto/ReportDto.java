package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private Long reportId;
    private Long clinicId;
    private String title;
    private String type;
    private LocalDateTime generatedAt;
    private Long generatedByUserId;
    private String content;
    private boolean isArchived;
}
