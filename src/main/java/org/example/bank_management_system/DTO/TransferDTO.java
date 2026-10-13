package org.example.bank_management_system.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    @NotBlank(message = "Source account number cannot be null")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$",
            message = "Source account number must follow XXXX-XXXX-XXXX-XXXX format")
    private String sourceAccountNumber;

    @NotBlank(message = "Destination account number cannot be null")
    @Pattern(regexp = "^[0-9]{4}(-[0-9]{4}){3}$",
            message = "Destination account number must follow XXXX-XXXX-XXXX-XXXX format")
    private String destinationAccountNumber;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Transfer amount must be greater than zero")
    private BigDecimal amount;
}