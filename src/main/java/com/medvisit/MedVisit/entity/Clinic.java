package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clinics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private boolean isActive;
}
