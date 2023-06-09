package com.gabri.coding.customerapi.service;

import com.gabri.coding.customerapi.dto.RoleDTO;
import com.gabri.coding.customerapi.dto.UserDTO;
import com.gabri.coding.customerapi.exceptions.ResourceNotFoundException;
import com.gabri.coding.customerapi.exceptions.ServiceLayerException;
import com.gabri.coding.customerapi.model.security.Role;
import com.gabri.coding.customerapi.model.security.User;
import com.gabri.coding.customerapi.repository.security.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDTO createUser(UserDTO userDTO){

        try {

            User userEntity = modelMapper.map(userDTO, User.class);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            User userSaved = userRepository.save(userEntity);
            return modelMapper.map(userSaved, UserDTO.class);

        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }

    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            List<UserDTO> userDTOList = users.stream().map(this::userEntityToDTO)
                    .collect(Collectors.toList());

            return userDTOList;

        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public UserDTO findUser(Long userId){
        try {
            return userRepository.findById(userId).map(this::userEntityToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        }catch (Exception e){

            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }


    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId){

        return userRepository.findById(userId).map(userAtDB ->
        {
            userAtDB.setRoles(this.roleDTOToRoleEntity(userDTO.getRoleDTOList()));
            return userEntityToDTO(userRepository.save(userAtDB));
        }).orElseThrow(() -> new ResourceNotFoundException("User", userId));

    }

    private UserDTO userEntityToDTO(User userEntity){
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

        List<RoleDTO> roleDTOList = userEntity.getRoles().stream().map(
                role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());

        userDTO.setRoleDTOList(roleDTOList);

        return userDTO;
    }

    private List<Role> roleDTOToRoleEntity(List<RoleDTO> roleDTOList){

        List<Role> roleList = roleDTOList.stream().map(
                role -> modelMapper.map(role, Role.class)).collect(Collectors.toList());

        return roleList;
    }
}
