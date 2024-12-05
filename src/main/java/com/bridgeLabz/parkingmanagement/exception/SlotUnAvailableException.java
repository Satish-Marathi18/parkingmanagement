package com.bridgeLabz.parkingmanagement.exception;

public class SlotUnAvailableException extends RuntimeException {
    private String message;
    public SlotUnAvailableException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
