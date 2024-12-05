package com.bridgeLabz.parkingmanagement.repo;

import com.bridgeLabz.parkingmanagement.dto.ReservationResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    Reservation findByVehicleNumber(@Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Correct the vehicle number format") String vehicleNumber);

    List<Reservation> findByEndTimeBeforeAndStatus(LocalTime now, String active);

    Reservation findBySlotId(Long slotId);

//    List<Reservation> findByStartTimeBeforeAndVehicleArrivedAndStatus(LocalTime localTime, boolean b, String active);
}
