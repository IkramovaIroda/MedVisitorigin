package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.ClientDto;
import com.medvisit.MedVisit.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientDto create(@RequestBody ClientDto dto) {
        return clientService.createClient(dto);
    }

    @GetMapping("/{id}")
    public ClientDto get(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    public ClientDto update(@PathVariable Long id, @RequestBody ClientDto dto) {
        return clientService.updateClient(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
