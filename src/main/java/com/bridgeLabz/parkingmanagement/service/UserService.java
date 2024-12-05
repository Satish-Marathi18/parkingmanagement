package com.bridgeLabz.parkingmanagement.service;

import com.bridgeLabz.parkingmanagement.dto.UserRequestDTO;
import com.bridgeLabz.parkingmanagement.dto.UserResponseDTO;

public interface UserService {
    UserRequestDTO createUser(UserRequestDTO userDTO);

    UserResponseDTO getUserDetail(Long userId);

    UserRequestDTO updataUser(UserRequestDTO userRequestDTO, Long userId);
}
