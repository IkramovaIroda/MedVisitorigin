package com.medvisit.MedVisit.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long clientId;

    private String firstName;
    private String lastName;
    private String patronymic;

    private LocalDate dateOfBirth;
    private String gender;

    private String phone;
    private String email;
    private String address;

    private String passportNumber;
    private String insuranceNumber;

    private boolean isActive;
    private Long clinicId;
}
