package com.bridgeLabz.parkingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportDTO {
    private Double totalRevenue;
    private long totalReservations;
    private long activereservations;
    private long cancelledReservations;
    private Map<String, Long> peakHours;
}
