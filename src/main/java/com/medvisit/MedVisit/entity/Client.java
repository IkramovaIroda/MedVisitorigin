package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
