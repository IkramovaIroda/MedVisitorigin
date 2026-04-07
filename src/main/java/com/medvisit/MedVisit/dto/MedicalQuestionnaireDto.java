package com.medvisit.MedVisit.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalQuestionnaireDto {

    private Long questionnaireId;
    private Long clientId;

    // Personal Information
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String occupation;
    private String maritalStatus;

    // Emergency Contact
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelation;

    // Medical History
    private String chronicDiseases;
    private String previousSurgeries;
    private String allergies;
    private String currentMedications;
    private String familyMedicalHistory;

    // Lifestyle
    private Boolean smokes;
    private Boolean drinksAlcohol;
    private String alcoholFrequency;
    private Boolean exercises;
    private String exerciseFrequency;
    private String dietType;

    // Current Health Status
    private String currentSymptoms;
    private String mainComplaints;
    private Double height;
    private Double weight;
    private String bloodType;

    // Insurance Information
    private String insuranceProvider;
    private String insuranceNumber;
    private String insuranceExpiryDate;

    // Additional Notes
    private String additionalNotes;

    // Metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lastUpdatedBy;
}
