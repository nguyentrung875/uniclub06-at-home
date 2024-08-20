package com.cybersoft.uniclub06.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorityDTO {
    private String username;
    private List<String> roles;
}
