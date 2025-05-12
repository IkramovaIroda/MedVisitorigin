package com.medvisit.MedVisit.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long employeeId;
}
