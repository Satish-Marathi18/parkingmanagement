package com.bridgeLabz.parkingmanagement.serviceImpl;

import com.bridgeLabz.parkingmanagement.dto.UserRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.UserResponseDTO;
import com.bridgeLabz.parkingmanagement.entity.User;
import com.bridgeLabz.parkingmanagement.exception.UserNotFoundException;
import com.bridgeLabz.parkingmanagement.repo.UserRepo;
import com.bridgeLabz.parkingmanagement.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserRequestDTO createUser(UserRequestDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepo.save(user);
        return userDTO;
    }

    @Override
    public UserResponseDTO getUserDetail(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setRegisteredVehicles(user.getRegisteredVehicles());
        return userResponseDTO;
    }

    @Override
    public UserRequestDTO updataUser(UserRequestDTO userRequestDTO, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        return mapToDTO(userRepo.save(user));
    }

    private UserRequestDTO mapToDTO(User user) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(user.getName());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setPhoneNumber(user.getPhoneNumber());
        return userRequestDTO;
    }
}
