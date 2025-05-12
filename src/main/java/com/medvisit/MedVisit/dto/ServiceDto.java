package com.medvisit.MedVisit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

    private Long serviceId;
    private String name;
    private String description;
    private Double price;
    private Long clinicId;
}
