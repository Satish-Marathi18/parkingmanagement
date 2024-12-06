package com.bridgeLabz.parkingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private Long userId;
    private Long slotId;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String vehicleType;
    private boolean vehicleArrived;
}
