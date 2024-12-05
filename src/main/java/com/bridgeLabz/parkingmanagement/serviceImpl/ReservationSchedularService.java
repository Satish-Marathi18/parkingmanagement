package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.entity.ParkingSlot;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.repo.ParkingSlotRepo;
import com.bridgeLabz.parkingmanagement.repo.ReservationRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationSchedularService {
    private ReservationRepo reservationRepo;
    private ParkingSlotRepo parkingSlotRepo;
    public ReservationSchedularService(ReservationRepo reservationRepo, ParkingSlotRepo parkingSlotRepo) {
        this.reservationRepo = reservationRepo;
        this.parkingSlotRepo = parkingSlotRepo;
    }

    @Scheduled(fixedRate = 60000)
    public void releaseExpireReservations() {
        List<Reservation> expiredReservations = reservationRepo.findByEndTimeBeforeAndStatus(LocalTime.now(),"ACTIVE");
        expiredReservations.forEach(reservation -> {
            reservation.setStatus("COMPLETED");
            ParkingSlot parkingSlot = parkingSlotRepo.findById(reservation.getSlotId()).orElse(null);
            if(parkingSlot != null) {
                parkingSlot.setIsAvailable(true);
                parkingSlotRepo.save(parkingSlot);
            }
            reservationRepo.save(reservation);
        });
    }
}