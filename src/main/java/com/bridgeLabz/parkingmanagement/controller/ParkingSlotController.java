package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.ParkingSlotDTO;
import com.bridgeLabz.parkingmanagement.service.ParkingSlotService;
import com.bridgeLabz.parkingmanagement.serviceImpl.ReservationSchedularService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parkingslot")
public class ParkingSlotController {
    private final ParkingSlotService parkingSlotService;
    private ReservationSchedularService reservationSchedularService;
    public ParkingSlotController(ParkingSlotService parkingSlotService, ReservationSchedularService reservationSchedularService) {
        this.parkingSlotService = parkingSlotService;
        this.reservationSchedularService = reservationSchedularService;
    }

    @PostMapping("/addslot")
    public ResponseEntity<ParkingSlotDTO> addParkingSlot(@RequestBody ParkingSlotDTO parkingSlotDTO) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(parkingSlotService.addParkingSlot(parkingSlotDTO), HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ParkingSlotDTO>> filterParkingSlotsByVehicleType() {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(parkingSlotService.filterParkingSlotsByVehicleType(),HttpStatus.OK);
    }

    @GetMapping("/getstatus")
    public ResponseEntity<Map<Long,String>> getStatus() {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(parkingSlotService.getStatus(),HttpStatus.OK);
    }

    @PostMapping("/reserve/{slotNumber}")
    public ResponseEntity<ParkingSlotDTO> reserveParkingSlot(@PathVariable Long slotNumber) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(parkingSlotService.reserveParkingSlot(slotNumber),HttpStatus.ACCEPTED);
    }

    @PostMapping("/release/{slotNumber}")
    public ResponseEntity<ParkingSlotDTO> releaseParkingSlot(@PathVariable Long slotNumber) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(parkingSlotService.releaseParkingSlot(slotNumber),HttpStatus.ACCEPTED);
    }
}
