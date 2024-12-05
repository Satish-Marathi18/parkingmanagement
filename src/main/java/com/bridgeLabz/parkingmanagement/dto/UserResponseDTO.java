package com.bridgeLabz.parkingmanagement.dto;

import com.bridgeLabz.parkingmanagement.entity.Reservation;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private List<Reservation> registeredVehicles;
}
