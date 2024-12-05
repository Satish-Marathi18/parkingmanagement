package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.ReservationRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.ReservationResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.repo.ReservationRepo;
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
    private final ReservationRepo reservationRepo;
    public ReservationController(ReservationService reservationService, ReservationSchedularService reservationSchedularService, ReservationRepo reservationRepo) {
        this.reservationService = reservationService;
        this.reservationRepo = reservationRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<ReservationResponseDTO> assignReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return new ResponseEntity<>(reservationService.addReservation(reservationRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationResponseDTO> cancelReservation(@PathVariable Long reservationId) {
        return new ResponseEntity<>(reservationService.cancelReservation(reservationId),HttpStatus.OK);
    }

    @GetMapping("/getreservation/{userId}")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations(@PathVariable Long userId) {
        return new ResponseEntity<>(reservationService.getAllReservations(userId),HttpStatus.OK);
    }

    @PutMapping("/arrive/{slotId}")
    public ResponseEntity<ReservationResponseDTO> arrive(@PathVariable Long slotId) {
        return new ResponseEntity<>(reservationService.arrive(slotId), HttpStatus.ACCEPTED);
    }



}
