package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserChangeRolesRequestDTO {
    private String login;
    private String[] roles;
//    private boolean agreemented;
}
