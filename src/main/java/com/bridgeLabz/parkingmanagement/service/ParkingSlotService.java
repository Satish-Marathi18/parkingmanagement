package com.bridgeLabz.parkingmanagement.service;

import com.bridgeLabz.parkingmanagement.dto.ParkingSlotDTO;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.List;

public interface ParkingSlotService {
    ParkingSlotDTO addParkingSlot(ParkingSlotDTO parkingSlotDTO);

    HashMap<Long,String> getStatus();

    List<ParkingSlotDTO> filterParkingSlotsByVehicleType();

    ParkingSlotDTO reserveParkingSlot(Long slotNumber);

    ParkingSlotDTO releaseParkingSlot(Long slotNumber);
}
