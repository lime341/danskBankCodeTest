package com.codetest.danskebankcodetest.rest;

import com.codetest.danskebankcodetest.entities.AccountEntity;
import com.codetest.danskebankcodetest.entities.TransactionEntity;
import com.codetest.danskebankcodetest.services.AccountManagementService;
import com.danskebankcodetest.api.AccountApi;
import com.danskebankcodetest.model.AccountDTO;
import com.danskebankcodetest.model.AccountInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountManagementRestController implements AccountApi {

    private final AccountManagementService accountManagementService;

    @Override
    public ResponseEntity<AccountDTO> createAccount(AccountInput accountInput) {
        return ResponseEntity.ok(accountManagementService.createAccount(accountInput));
    }

    @Override
    public ResponseEntity<BigDecimal> depositMoney(String accountId, BigDecimal body) {
        AccountEntity account = accountManagementService.getAccount(UUID.fromString(accountId));
        accountManagementService.depositMoney(new TransactionEntity(body,account));
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String accountId) {
        return ResponseEntity.ok(accountManagementService.getBalance(UUID.fromString(accountId)));
    }

    @Override
    public ResponseEntity<BigDecimal> getTransaction(Integer limit, String accountId) {
        return null;
    }

    @Override
    public ResponseEntity<BigDecimal> withdrawMoney(String accountId, BigDecimal body) {
        AccountEntity account = accountManagementService.getAccount(UUID.fromString(accountId));
        accountManagementService.withdrawMoney(new TransactionEntity(body,account));
        return ResponseEntity.ok(body);
    }
}
