package org.example.bank_management_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Api.ApiResponse;
import org.example.bank_management_system.DTO.AccountCreateDTO;
import org.example.bank_management_system.Model.Account;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> createAccount(
            @Valid @RequestBody AccountCreateDTO dto,
            @AuthenticationPrincipal User user) {

        accountService.createAccount(dto.getAccountNumber(), user);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Bank account created successfully"));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Account>> getMyAccounts(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(accountService.getMyAccounts(user));
    }

    @GetMapping("/my/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Account> getMyAccount(
            @PathVariable Integer id,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(accountService.getMyAccount(id, user));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateAccount(
            @PathVariable Integer id,
            @Valid @RequestBody Account account) {

        accountService.updateAccount(id, account);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Account updated successfully"));
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<ApiResponse> activateAccount(@PathVariable Integer id) {
        accountService.activateAccount(id);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Account activated successfully"));
    }

    @PutMapping("/{id}/block")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<ApiResponse> blockAccount(@PathVariable Integer id) {
        accountService.blockAccount(id);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Bank account blocked successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Account deleted successfully"));
    }
}