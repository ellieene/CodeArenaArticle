package com.example.article.model.dto;

import com.example.article.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String email;
    private String description;
    private Role role;
    private int points;
    private boolean owner;
}
