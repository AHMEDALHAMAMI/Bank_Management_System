package org.example.bank_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountCreateDTO {

    @NotBlank(message = "Account number cannot be null")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$",
            message = "Account number must follow XXXX-XXXX-XXXX-XXXX format")
    private String accountNumber;
}