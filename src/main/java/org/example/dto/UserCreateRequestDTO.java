package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateRequestDTO {
    private String login;
    private String password;
    private String[] roles;
//    private boolean agreemented;
}
