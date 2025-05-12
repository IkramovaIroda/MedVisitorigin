package com.medvisit.MedVisit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "visit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action; // e.g., CREATED, UPDATED, CANCELLED

    private LocalDateTime timestamp;
}
