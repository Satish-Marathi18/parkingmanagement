package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.dto.ReservationRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.ReservationResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.ParkingSlot;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.entity.User;
import com.bridgeLabz.parkingmanagement.exception.ReservationConflictException;
import com.bridgeLabz.parkingmanagement.exception.SlotUnAvailableException;
import com.bridgeLabz.parkingmanagement.exception.UserNotFoundException;
import com.bridgeLabz.parkingmanagement.repo.ParkingSlotRepo;
import com.bridgeLabz.parkingmanagement.repo.ReservationRepo;
import com.bridgeLabz.parkingmanagement.repo.UserRepo;
import com.bridgeLabz.parkingmanagement.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepo reservationRepo;
    private ParkingSlotRepo parkingSlotRepo;
    private UserRepo userRepo;
    public ReservationServiceImpl(ReservationRepo reservationRepo, ParkingSlotRepo parkingSlotRepo, UserRepo userRepo) {
        this.reservationRepo = reservationRepo;
        this.parkingSlotRepo = parkingSlotRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ReservationResponseDTO addReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation existingReservation = reservationRepo.findByVehicleNumber(reservationRequestDTO.getVehicleNumber());
        if (existingReservation != null && existingReservation.getStatus().equals("ACTIVE")) {
            throw new ReservationConflictException("Reservation already exists for this vehicle.");
        }

        ParkingSlot parkingSlot = parkingSlotRepo.findById(reservationRequestDTO.getSlotId()).orElse(null);
        if(!reservationRequestDTO.getVehicleType().equalsIgnoreCase(parkingSlot.getVehicleType())){
            throw new SlotUnAvailableException("This Slot is not reserved for this vehicle.");
        }

        ParkingSlot slot = parkingSlotRepo.findById(reservationRequestDTO.getSlotId())
                .orElseThrow(() -> new SlotUnAvailableException("Invalid slot ID. Slot not found."));
        if (!slot.getIsAvailable()) {
            throw new SlotUnAvailableException("The selected slot is not available.");
        }

        User user = userRepo.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Invalid user ID. User not found."));

        Reservation reservation = new Reservation();
        reservation.setUser(user); // Set the user
        reservation.setSlotId(slot.getId()); // Set the slot ID
        reservation.setVehicleNumber(reservationRequestDTO.getVehicleNumber());
        reservation.setStartTime(reservationRequestDTO.getStartTime());
        reservation.setEndTime(reservationRequestDTO.getEndTime());
        reservation.setVehicleType(reservationRequestDTO.getVehicleType());
        reservation.setStatus("ACTIVE");
        reservation.setVehicleArrived(false);

        Reservation savedReservation = reservationRepo.save(reservation);

        user.getRegisteredVehicles().add(savedReservation);

        slot.setIsAvailable(false);
        parkingSlotRepo.save(slot);

        return mapToDTO(savedReservation);
    }

    @Override
    public ReservationResponseDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID."));
        ParkingSlot slot = parkingSlotRepo.findById(reservation.getSlotId()).orElseThrow(() -> new IllegalArgumentException("You have not registered slot."));
        if(reservation.getStatus().equals("ACTIVE") && !slot.getIsAvailable()) {
            reservation.setStatus("CANCELLED");
            slot.setIsAvailable(true);
        }
        Reservation savedReservation = reservationRepo.save(reservation);
        return mapToDTO(savedReservation);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Invalid user ID."));
        List<Reservation> reservations = user.getRegisteredVehicles();
        return mapToDTOList(reservations);
    }

    @Override
    public ReservationResponseDTO arrive(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID."));
        if(reservation.getStatus().equals("ACTIVE")) {
            reservation.setVehicleArrived(true);
            return mapToDTO(reservationRepo.save(reservation));
        }
        else
            throw new IllegalStateException("Reservation status is not active.");
    }

    private List<ReservationResponseDTO> mapToDTOList(List<Reservation> reservations) {
       return reservations.stream()
                .map(reservation -> mapToDTO(reservation)).toList();
    }


    private ReservationResponseDTO mapToDTO(Reservation reservation) {
        ReservationResponseDTO reservationDTO = new ReservationResponseDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserId(reservation.getUser().getId());
        reservationDTO.setSlotId(reservation.getSlotId());
        reservationDTO.setVehicleNumber(reservation.getVehicleNumber());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setVehicleType(reservation.getVehicleType());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setVehicleArrived(reservation.getVehicleArrived());
        return reservationDTO;

    }
}
