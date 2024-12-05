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
    public ParkingSlotController(ParkingSlotService parkingSlotService, ReservationSchedularService reservationSchedularService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping("/addslot")
    public ResponseEntity<ParkingSlotDTO> addParkingSlot(@RequestBody ParkingSlotDTO parkingSlotDTO) {
        return new ResponseEntity<>(parkingSlotService.addParkingSlot(parkingSlotDTO), HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ParkingSlotDTO>> filterParkingSlotsByVehicleType() {
        return new ResponseEntity<>(parkingSlotService.filterParkingSlotsByVehicleType(),HttpStatus.OK);
    }

    @GetMapping("/getstatus")
    public ResponseEntity<Map<Long,String>> getStatus() {
        return new ResponseEntity<>(parkingSlotService.getStatus(),HttpStatus.OK);
    }

    @PostMapping("/reserve/{slotNumber}")
    public ResponseEntity<ParkingSlotDTO> reserveParkingSlot(@PathVariable Long slotNumber) {
        return new ResponseEntity<>(parkingSlotService.reserveParkingSlot(slotNumber),HttpStatus.ACCEPTED);
    }

    @PostMapping("/release/{slotNumber}")
    public ResponseEntity<ParkingSlotDTO> releaseParkingSlot(@PathVariable Long slotNumber) {
        return new ResponseEntity<>(parkingSlotService.releaseParkingSlot(slotNumber),HttpStatus.ACCEPTED);
    }
}
