package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long visitId;

    private Long clientId;
    private Long employeeId;
    private Long serviceId;
    private Long scheduleId;
    private Long clinicId;

    private LocalDateTime visitDateTime;
    private String status;
    private String notes;
}
