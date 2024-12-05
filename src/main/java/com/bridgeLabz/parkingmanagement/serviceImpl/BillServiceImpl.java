package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.dto.BillResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.repo.BillRepo;
import com.bridgeLabz.parkingmanagement.repo.ReservationRepo;
import com.bridgeLabz.parkingmanagement.service.BillService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BillServiceImpl implements BillService {
    private BillRepo billRepo;
    private ReservationRepo reservationRepo;
    private EmailService emailService;
    public BillServiceImpl(BillRepo billRepo, ReservationRepo reservationRepo, EmailService emailService) {
        this.billRepo = billRepo;
        this.reservationRepo = reservationRepo;
        this.emailService = emailService;
    }

    @Override
    public BillResponseDTO generateBill(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId).get();
        if(billRepo.existsByReservation(reservation)){
            throw new IllegalArgumentException("Bill already exists for this reservation");
        }
        Long duration = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes();
        Double durationInMinutes = duration / 60.0;
        Bill bill = new Bill();
        if(reservation.getVehicleType().equalsIgnoreCase("Bike")) {
            bill.setAmount((durationInMinutes*1.0));
        }
        if(reservation.getVehicleType().equalsIgnoreCase("Car")) {
            bill.setAmount(durationInMinutes*3.0);
        }
        if(reservation.getVehicleType().equalsIgnoreCase("Truck")) {
            bill.setAmount(durationInMinutes*5.0);
        }
        bill.setPaymentStatus("Unpaid");
        bill.setReservation(reservation);
        return mapToDTO(billRepo.save(bill));
    }

    @Override
    public String payBill(Long billId) {
        Bill bill = billRepo.findById(billId).orElse(null);
        if(bill.getPaymentStatus().equalsIgnoreCase("Paid")) {
            throw new IllegalArgumentException("Bill payment already paid");
        }
        bill.setPaymentStatus("Paid");
        billRepo.save(bill);
        String subject = "Payment successful";
        String text = "you have paid your parking reservation bill "+bill.getAmount()+"$ successfully.\nVisit again...";
        emailService.sendEmail(bill.getReservation().getUser().getEmail(),subject,text);
        return "Payment Successful";
    }

    private BillResponseDTO mapToDTO(Bill bill) {
        BillResponseDTO billDTO = new BillResponseDTO();
        billDTO.setId(bill.getId());
        billDTO.setAmount(bill.getAmount());
        billDTO.setPaymentStatus(bill.getPaymentStatus());
        billDTO.setReservation(bill.getReservation());
        return billDTO;
    }

}
