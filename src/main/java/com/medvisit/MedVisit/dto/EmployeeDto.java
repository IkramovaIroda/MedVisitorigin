package com.medvisit.MedVisit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long employeeId;
    private String fullName;
    private String position;
    private String specialization;
    private String phone;
    private String email;
    private Long clinicId;
}
