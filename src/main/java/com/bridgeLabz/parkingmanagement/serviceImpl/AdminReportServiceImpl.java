package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.dto.AdminReportDTO;
import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import com.bridgeLabz.parkingmanagement.repo.BillRepo;
import com.bridgeLabz.parkingmanagement.repo.ReservationRepo;
import com.bridgeLabz.parkingmanagement.service.AdminReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminReportServiceImpl implements AdminReportService {
    private ReservationRepo reservationRepo;
    private BillRepo billRepo;
    public AdminReportServiceImpl(ReservationRepo reservationRepo, BillRepo billRepo) {
        this.reservationRepo = reservationRepo;
        this.billRepo = billRepo;
    }

    @Override
    public AdminReportDTO getAdminReport() {
        List<Reservation> reservations = reservationRepo.findAll();
        List<Bill> bills = billRepo.findAll();
        Double revenue = bills.stream().filter(bill -> bill.getPaymentStatus().equals("Paid")).mapToDouble(bill -> bill.getAmount()).sum();
        Long totalReservation = (long) reservations.size();
        Long cancelledReservation = reservations.stream().filter(reservation -> reservation.getStatus().equals("CANCELLED")).count();
        Long activeReservation = reservations.stream().filter(reservation -> reservation.getStatus().equals("ACTIVE")).count();
        Map<String, Long> peakTime = reservations.stream()
                .filter(reservation -> reservation.getStartTime() != null)
                .collect(Collectors.groupingBy(
                        reservation -> String.format("%02d:00", reservation.getStartTime().getHour()),
                        Collectors.counting()
                ));
        AdminReportDTO adminReportDTO = new AdminReportDTO();
        adminReportDTO.setTotalRevenue(revenue);
        adminReportDTO.setTotalReservations(totalReservation);
        adminReportDTO.setCancelledReservations(cancelledReservation);
        adminReportDTO.setActivereservations(activeReservation);
        adminReportDTO.setPeakHours(peakTime);
        return adminReportDTO;
    }
}
