package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    private String title;

    private String type;

    private LocalDateTime generatedAt;

    @ManyToOne
    @JoinColumn(name = "generated_by_user_id")
    private User generatedBy;

    @Column(length = 10000)
    private String content;

    private boolean isArchived;
}
