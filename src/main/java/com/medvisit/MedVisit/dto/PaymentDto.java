package com.medvisit.MedVisit.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private BigDecimal amount;
    private String method;
    private String status;
    private LocalDateTime paymentDate;
    private Long visitId;
    private Long clientId;
    private String note;
}
