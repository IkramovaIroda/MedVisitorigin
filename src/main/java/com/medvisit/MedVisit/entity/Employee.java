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

    @ManyToMany
    @JoinTable(
        name = "employee_services",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private java.util.List<Service> services = new java.util.ArrayList<>();
}
