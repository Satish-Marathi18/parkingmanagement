package com.bridgeLabz.parkingmanagement.service;

import com.bridgeLabz.parkingmanagement.dto.BillResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Bill;
import org.springframework.http.HttpStatusCode;

public interface BillService {
    BillResponseDTO generateBill(Long reservationId);

    String payBill(Long billId);
}
