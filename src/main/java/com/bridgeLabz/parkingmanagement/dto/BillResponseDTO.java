package com.bridgeLabz.parkingmanagement.dto;

import com.bridgeLabz.parkingmanagement.entity.Reservation;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDTO {
    private Long id;
    private Reservation reservation;
    private Double amount;
    private String paymentStatus;
}
