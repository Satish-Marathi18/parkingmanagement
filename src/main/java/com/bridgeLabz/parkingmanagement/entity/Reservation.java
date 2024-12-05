package com.bridgeLabz.parkingmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable=true, updatable=true)
    private Long slotId;
    private String vehicleNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String vehicleType;
    private boolean vehicleArrived;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    @JsonIgnore
    private Bill bill;


    public boolean getVehicleArrived() {
        return vehicleArrived;
    }
}
