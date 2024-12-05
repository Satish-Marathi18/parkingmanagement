package com.bridgeLabz.parkingmanagement.service;

import com.bridgeLabz.parkingmanagement.dto.ReservationRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.ReservationResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationResponseDTO addReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO cancelReservation(Long reservationId);

    List<ReservationResponseDTO> getAllReservations(Long userId);
}
