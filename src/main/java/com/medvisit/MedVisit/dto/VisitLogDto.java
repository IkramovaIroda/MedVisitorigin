package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitLogDto {
    private Long logId;
    private Long visitId;
    private Long userId;
    private String action;
    private LocalDateTime timestamp;
}
