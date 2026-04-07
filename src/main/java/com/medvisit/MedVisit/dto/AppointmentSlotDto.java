package com.medvisit.MedVisit.dto;

import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSlotDto {

    private Long slotId;
    private Long scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isBooked;
    private Long visitId;
}
