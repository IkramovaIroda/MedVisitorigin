package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto dto);
    ClientDto getClientById(Long id);
    List<ClientDto> getAllClients();
    ClientDto updateClient(Long id, ClientDto dto);
    void deleteClient(Long id);
}
