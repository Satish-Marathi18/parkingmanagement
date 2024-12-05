package com.bridgeLabz.parkingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotDTO {
    private Long slotNumber;
    private Integer level;
    private String vehicleType;
}
