package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
