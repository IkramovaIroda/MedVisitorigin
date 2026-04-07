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
    private Integer duration; // Duration in minutes
    private Long clinicId;

    public ServiceDto(Long serviceId, String name, String description, Double price, Long clinicId) {
    }
}
