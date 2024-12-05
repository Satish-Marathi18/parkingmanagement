package com.bridgeLabz.parkingmanagement.repo;

import com.bridgeLabz.parkingmanagement.dto.ParkingSlotDTO;
import com.bridgeLabz.parkingmanagement.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepo extends JpaRepository<ParkingSlot, Long> {
    public ParkingSlot findBySlotNumber(Long slotNumber);
}
