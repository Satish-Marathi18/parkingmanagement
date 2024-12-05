package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.BillResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    private BillService billService;
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("generate/{reservationId}")
    public ResponseEntity<BillResponseDTO> generateBill(@PathVariable Long reservationId) {
        return new ResponseEntity<>(billService.generateBill(reservationId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/pay/{billId}")
    public ResponseEntity<String> payBill(@PathVariable Long billId) {
        return new ResponseEntity<>(billService.payBill(billId), HttpStatus.ACCEPTED);
    }
}
