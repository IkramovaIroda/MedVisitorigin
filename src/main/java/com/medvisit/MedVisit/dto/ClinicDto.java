package com.medvisit.MedVisit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDto {
    private Long clinicId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private boolean isActive;
}
