package org.example.bank_management_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Api.ApiResponse;
import org.example.bank_management_system.DTO.DepositDTO;
import org.example.bank_management_system.DTO.TransferDTO;
import org.example.bank_management_system.DTO.WithdrawDTO;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Service.AccountService;
import org.example.bank_management_system.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @PostMapping("/{id}/deposit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> deposit(
            @PathVariable Integer id,
            @Valid @RequestBody DepositDTO dto,
            @AuthenticationPrincipal User user) {

        accountService.getMyAccount(id, user);
        transactionService.deposit(id, dto.getAmount());

        return ResponseEntity.status(200)
                .body(new ApiResponse("Deposit completed successfully"));
    }

    @PostMapping("/{id}/withdraw")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> withdraw(
            @PathVariable Integer id,
            @Valid @RequestBody WithdrawDTO dto,
            @AuthenticationPrincipal User user) {

        accountService.getMyAccount(id, user);
        transactionService.withdraw(id, dto.getAmount());

        return ResponseEntity.status(200)
                .body(new ApiResponse("Withdrawal completed successfully"));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> transfer(
            @Valid @RequestBody TransferDTO dto,
            @AuthenticationPrincipal User user) {

        var source = accountService.getMyAccounts(user).stream()
                .filter(account -> account.getAccountNumber()
                        .equals(dto.getSourceAccountNumber()))
                .findFirst()
                .orElse(null);

        if (source == null) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Source account not found for this customer"));
        }

        transactionService.transfer(
                dto.getSourceAccountNumber(),
                dto.getDestinationAccountNumber(),
                dto.getAmount());

        return ResponseEntity.status(200)
                .body(new ApiResponse("Transfer completed successfully"));
    }
}