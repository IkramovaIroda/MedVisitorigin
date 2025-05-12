package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String fullName;
    private String position;
    private String specialization;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
