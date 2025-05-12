package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cabinets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cabinetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer floor;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    private String description;

    private boolean active = true;
}
