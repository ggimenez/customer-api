package com.gabri.coding.customerapi.service;

import com.gabri.coding.customerapi.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO findUser(Long userId);
    UserDTO updateUser(UserDTO userDTO, Long userId);
}
