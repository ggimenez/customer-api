package com.gabri.coding.customerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDTO {
    private String userName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDTO(){}

    public UserDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    List<RoleDTO> roleDTOList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoleDTOList() {
        return roleDTOList;
    }

    public void setRoleDTOList(List<RoleDTO> roleDTOList) {
        this.roleDTOList = roleDTOList;
    }
}
