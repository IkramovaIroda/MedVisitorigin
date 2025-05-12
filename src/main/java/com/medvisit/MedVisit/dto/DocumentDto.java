package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private Long documentId;
    private String title;
    private String content;
    private Long visitId;
    private Long createdBy;
    private LocalDateTime createdAt;
}
