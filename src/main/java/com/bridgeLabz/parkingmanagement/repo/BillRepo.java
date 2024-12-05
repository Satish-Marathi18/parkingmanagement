package com.bridgeLabz.parkingmanagement.repo;

import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    boolean existsByReservation(Reservation reservation);
}
