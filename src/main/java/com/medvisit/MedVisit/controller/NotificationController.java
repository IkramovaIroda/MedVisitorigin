package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.NotificationDto;
import com.medvisit.MedVisit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationDto create(@RequestBody NotificationDto dto) {
        return notificationService.create(dto);
    }

    @GetMapping("/{id}")
    public NotificationDto get(@PathVariable Long id) {
        return notificationService.getById(id);
    }

    @GetMapping
    public List<NotificationDto> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/recipient/{userId}")
    public List<NotificationDto> getByRecipient(@PathVariable Long userId) {
        return notificationService.getByRecipient(userId);
    }

    @PutMapping("/read/{id}")
    public NotificationDto markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }
}
