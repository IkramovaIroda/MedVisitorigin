package com.medvisit.MedVisit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabinetDto {
    private Long cabinetId;
    private String name;
    private Integer floor;
    private Long clinicId;
    private String description;
    private boolean active;
}
