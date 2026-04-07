package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String name;
    private String description;
    private Double price;
    private Integer duration; // Duration in minutes

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
