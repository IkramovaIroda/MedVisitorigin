package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.ClientDto;
import com.medvisit.MedVisit.entity.Client;
import com.medvisit.MedVisit.entity.Clinic;
import com.medvisit.MedVisit.repository.ClientRepository;
import com.medvisit.MedVisit.repository.ClinicRepository;
import com.medvisit.MedVisit.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public ClientDto createClient(ClientDto dto) {
        Client client = new Client();
        BeanUtils.copyProperties(dto, client);
        client.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
        return toDto(clientRepository.save(client));
    }

    @Override
    public ClientDto getClientById(Long id) {
        return clientRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        BeanUtils.copyProperties(dto, client, "clientId");
        client.setClinic(clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
        return toDto(clientRepository.save(client));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    private ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        BeanUtils.copyProperties(client, dto);
        dto.setClinicId(client.getClinic() != null ? client.getClinic().getClinicId() : null);
        return dto;
    }
}
