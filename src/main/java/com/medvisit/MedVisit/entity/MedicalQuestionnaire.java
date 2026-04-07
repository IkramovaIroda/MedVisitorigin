package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_questionnaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionnaireId;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

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
    @Column(length = 2000)
    private String chronicDiseases;

    @Column(length = 2000)
    private String previousSurgeries;

    @Column(length = 2000)
    private String allergies;

    @Column(length = 2000)
    private String currentMedications;

    @Column(length = 2000)
    private String familyMedicalHistory;

    // Lifestyle
    private Boolean smokes;
    private Boolean drinksAlcohol;
    private String alcoholFrequency;
    private Boolean exercises;
    private String exerciseFrequency;
    private String dietType;

    // Current Health Status
    @Column(length = 2000)
    private String currentSymptoms;

    @Column(length = 2000)
    private String mainComplaints;

    private Double height;
    private Double weight;
    private String bloodType;

    // Insurance Information
    private String insuranceProvider;
    private String insuranceNumber;
    private String insuranceExpiryDate;

    // Additional Notes
    @Column(length = 3000)
    private String additionalNotes;

    // Metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lastUpdatedBy; // User ID of doctor who last edited
}
