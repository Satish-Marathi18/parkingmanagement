package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.ReservationRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.ReservationResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.service.ReservationService;
import com.bridgeLabz.parkingmanagement.serviceImpl.ReservationSchedularService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private ReservationSchedularService reservationSchedularService;
    public ReservationController(ReservationService reservationService, ReservationSchedularService reservationSchedularService) {
        this.reservationService = reservationService;
        this.reservationSchedularService = reservationSchedularService;
    }

    @PostMapping("/add")
    public ResponseEntity<ReservationResponseDTO> assignReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(reservationService.addReservation(reservationRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationResponseDTO> cancelReservation(@PathVariable Long reservationId) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(reservationService.cancelReservation(reservationId),HttpStatus.OK);
    }

    @GetMapping("/getreservation/{userId}")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations(@PathVariable Long userId) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(reservationService.getAllReservations(userId),HttpStatus.OK);
    }


}
