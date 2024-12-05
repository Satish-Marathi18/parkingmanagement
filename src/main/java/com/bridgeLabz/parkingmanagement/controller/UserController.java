package com.bridgeLabz.parkingmanagement.controller;

import com.bridgeLabz.parkingmanagement.dto.UserRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.UserResponseDTO;
import com.bridgeLabz.parkingmanagement.service.UserService;
import com.bridgeLabz.parkingmanagement.serviceImpl.ReservationSchedularService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private ReservationSchedularService reservationSchedularService;
    public UserController(UserService userService, ReservationSchedularService reservationSchedularService) {
        this.userService = userService;
        this.reservationSchedularService = reservationSchedularService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserRequestDTO> createUser(@RequestBody UserRequestDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/userdetail/{userId}")
    public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable Long userId) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(userService.getUserDetail(userId),HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserRequestDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO, @PathVariable Long userId) {
        reservationSchedularService.releaseExpireReservations();
        return new ResponseEntity<>(userService.updataUser(userRequestDTO,userId),HttpStatus.OK);
    }
}
