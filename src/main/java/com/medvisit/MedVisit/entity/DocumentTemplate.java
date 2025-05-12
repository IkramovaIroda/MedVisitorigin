package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_templates")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    private String title;

    @Column(length = 10000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean isActive;
}
