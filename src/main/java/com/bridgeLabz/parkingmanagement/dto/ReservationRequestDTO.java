package com.bridgeLabz.parkingmanagement.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {
    private Long userId;
    private Long slotId;
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Correct the vehicle number format")
    private String vehicleNumber;
    @FutureOrPresent(message = "Start time should be future or present")
    private LocalTime startTime;
    @Future(message = "End time should be future")
    private LocalTime endTime;
    private String vehicleType;
}
