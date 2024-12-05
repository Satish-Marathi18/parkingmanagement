package com.bridgeLabz.parkingmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlot {
    @Id
    @GeneratedValue
    private Long id;
    private Long slotNumber;
    private Integer level;
    private Boolean isAvailable = true;
    private String vehicleType;
}
