package com.bridgeLabz.parkingmanagement.exception;

public class ReservationConflictException extends RuntimeException{
    private String message;

    public ReservationConflictException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
