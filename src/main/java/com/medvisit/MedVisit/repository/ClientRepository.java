package com.medvisit.MedVisit.repository;

import com.medvisit.MedVisit.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
