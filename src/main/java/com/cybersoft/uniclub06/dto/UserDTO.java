package com.cybersoft.uniclub06.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String email;
    private String fullName;
    private RoleDTO role;
}
