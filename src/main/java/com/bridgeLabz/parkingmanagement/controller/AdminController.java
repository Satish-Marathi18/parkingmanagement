package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.AdminReportDTO;
import com.bridgeLabz.parkingmanagement.entity.Bill;
import com.bridgeLabz.parkingmanagement.repo.BillRepo;
import com.bridgeLabz.parkingmanagement.service.AdminReportService;
import com.bridgeLabz.parkingmanagement.serviceImpl.AdminReportServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminReportService adminReportService;

    public AdminController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @GetMapping("/report")
   public ResponseEntity<AdminReportDTO> getDetailedReport() {
        return new ResponseEntity<>(adminReportService.getAdminReport(), HttpStatus.OK);
    }
}
