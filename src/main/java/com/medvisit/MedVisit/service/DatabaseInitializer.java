package com.medvisit.MedVisit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final AppointmentSlotService appointmentSlotService;
    private final com.medvisit.MedVisit.service.ScheduleService scheduleService;

    @Override
    public void run(String... args) {
        System.out.println("Initializing appointment slots from existing schedules...");
        
        try {
            // Get all schedules and generate slots for them
            var allSchedules = scheduleService.getAll();
            int slotCount = 0;
            
            for (var schedule : allSchedules) {
                try {
                    // Generate slots with default 30-minute duration
                    appointmentSlotService.generateTimeSlotsForSchedule(schedule.getScheduleId(), 30);
                    slotCount++;
                    System.out.println("Generated slots for schedule " + schedule.getScheduleId() + 
                                     " (" + schedule.getDate() + ")");
                } catch (Exception e) {
                    System.err.println("Error generating slots for schedule " + schedule.getScheduleId() + 
                                     ": " + e.getMessage());
                }
            }
            
            System.out.println("Appointment slot initialization complete!");
            System.out.println("Total schedules processed: " + slotCount);
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
