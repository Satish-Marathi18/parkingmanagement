package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.repo.BillRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BillRepo billRepo;

    public AdminController(BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    @GetMapping("/revenue")
    public String getTotalRevenue() {
        List<Bill> bills = billRepo.findAll();
        Double totalRevenue = 0.0;
        for (Bill bill : bills) {
            totalRevenue += bill.getAmount();
        }
        return "Total Revenue: " + totalRevenue;
    }
}
