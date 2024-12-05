package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.dto.ParkingSlotDTO;
import com.bridgeLabz.parkingmanagement.entity.ParkingSlot;
import com.bridgeLabz.parkingmanagement.exception.SlotUnAvailableException;
import com.bridgeLabz.parkingmanagement.repo.ParkingSlotRepo;
import com.bridgeLabz.parkingmanagement.service.ParkingSlotService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {
    private ParkingSlotRepo parkingSlotRepo;
    public ParkingSlotServiceImpl(ParkingSlotRepo parkingSlotRepo) {
        this.parkingSlotRepo = parkingSlotRepo;
    }

    @Override
    public ParkingSlotDTO addParkingSlot(ParkingSlotDTO parkingSlotDTO) {
        ParkingSlot getParkingSlot = parkingSlotRepo.findBySlotNumber(parkingSlotDTO.getSlotNumber());
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(parkingSlotDTO.getSlotNumber());
        parkingSlot.setLevel(parkingSlotDTO.getLevel());
        parkingSlot.setVehicleType(parkingSlotDTO.getVehicleType());
        if(getParkingSlot==null) {
            parkingSlotRepo.save(parkingSlot);
            return parkingSlotDTO;
        }
        else
            throw new SlotUnAvailableException("Parking slot not available");
    }

    @Override
    public HashMap<Long, String> getStatus() {
        HashMap<Long, String> status = new HashMap<>();
        List<ParkingSlot> parkingSlots = parkingSlotRepo.findAll();
        parkingSlots.forEach(parkingSlot -> {status.put(parkingSlot.getSlotNumber(), parkingSlot.getIsAvailable()?"available":"occupied");});
        return status;
    }

    @Override
    public List<ParkingSlotDTO> filterParkingSlotsByVehicleType() {
        List<ParkingSlot> parkingSlots = parkingSlotRepo.findAll();
        List<ParkingSlot> filteredParkingSlots = parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getVehicleType() != null)
                .sorted((slot1, slot2) -> slot1.getVehicleType().compareToIgnoreCase(slot2.getVehicleType()))
                .toList();
        return mapToDTOList(filteredParkingSlots);
    }

    @Override
    public ParkingSlotDTO reserveParkingSlot(Long slotNumber) {
        ParkingSlot parkingSlot = parkingSlotRepo.findBySlotNumber(slotNumber);
        if(parkingSlot==null || !parkingSlot.getIsAvailable()) {
            throw new SlotUnAvailableException("Slot number "+slotNumber+" does not exist");
        }
        else {
            parkingSlot.setIsAvailable(false);
            return mapToDTO(parkingSlotRepo.save(parkingSlot));
        }
    }

    @Override
    public ParkingSlotDTO releaseParkingSlot(Long slotNumber) {
        ParkingSlot parkingSlot = parkingSlotRepo.findBySlotNumber(slotNumber);
        if(parkingSlot==null || parkingSlot.getIsAvailable()) {
            throw new SlotUnAvailableException("Slot number "+slotNumber+" does not exist");
        }
        else {
            parkingSlot.setIsAvailable(true);
            return mapToDTO(parkingSlotRepo.save(parkingSlot));
        }
    }

    private List<ParkingSlotDTO> mapToDTOList(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().map(parkingSlot -> {
            ParkingSlotDTO parkingSlotDTO = new ParkingSlotDTO();
            parkingSlotDTO.setSlotNumber(parkingSlot.getSlotNumber());
            parkingSlotDTO.setLevel(parkingSlot.getLevel());
            parkingSlotDTO.setVehicleType(parkingSlot.getVehicleType());
            return parkingSlotDTO;
        }).toList();
    }

    private ParkingSlotDTO mapToDTO(ParkingSlot parkingSlot) {
        ParkingSlotDTO parkingSlotDTO = new ParkingSlotDTO();
        parkingSlotDTO.setSlotNumber(parkingSlot.getSlotNumber());
        parkingSlotDTO.setLevel(parkingSlot.getLevel());
        parkingSlotDTO.setVehicleType(parkingSlot.getVehicleType());
        return parkingSlotDTO;
    }
}
