package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountGetByIdResponseDTO {
    private String id;
    private int balance;

}
