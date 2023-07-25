package com.codetest.danskebankcodetest.services;

import com.codetest.danskebankcodetest.entities.AccountEntity;
import com.codetest.danskebankcodetest.entities.TransactionEntity;
import com.codetest.danskebankcodetest.repositories.AccountManagementRepository;
import com.danskebankcodetest.model.AccountDTO;
import com.danskebankcodetest.model.AccountInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountManagementServiceTest {

    @Autowired
    AccountManagementRepository accountManagementRepository;

    @Autowired
    AccountManagementService accountManagementService;

    @Test
    void testRepo() {
        var account = AccountEntity.builder().name("TestAccount").build();
        var accountFromDb = accountManagementRepository.save(account);

        assertEquals(account, accountFromDb);
    }

    @Test
    void createAccount() {
        AccountInput input = new AccountInput().customerId("testId").name("testName");
        accountManagementService.createAccount(input);

        assertEquals(1, accountManagementRepository.findAll().size());
    }

    @Test
    void getBalance() {
        AccountInput input = new AccountInput().customerId("testId").name("testName");
        AccountDTO accountDTO = accountManagementService.createAccount(input);
        AccountEntity account = accountManagementService.getAccount(UUID.fromString(accountDTO.getId()));

        BigDecimal balance = accountManagementService.getBalance(account.getAccountId());

        assertNotEquals(null, balance);
        assertEquals(BigInteger.ZERO,balance.toBigInteger());
    }

    @Test
    void depositMoney() {
        AccountInput input = new AccountInput().customerId("testId").name("testName");
        AccountDTO accountDTO = accountManagementService.createAccount(input);
        AccountEntity account = accountManagementService.getAccount(UUID.fromString(accountDTO.getId()));

        TransactionEntity transaction = new TransactionEntity(BigDecimal.valueOf(50), account);
        accountManagementService.depositMoney(transaction);

        BigDecimal balance = accountManagementService.getBalance(account.getAccountId());

        assertEquals(BigInteger.valueOf(50), balance.toBigInteger());
    }

    @Test
    void withdrawMoney() {
        AccountInput input = new AccountInput().customerId("testId").name("testName");
        AccountDTO accountDTO = accountManagementService.createAccount(input);
        AccountEntity account = accountManagementService.getAccount(UUID.fromString(accountDTO.getId()));

        TransactionEntity depositTransaction = new TransactionEntity(BigDecimal.valueOf(50), account);
        accountManagementService.depositMoney(depositTransaction);

        TransactionEntity withdrawTransaction = new TransactionEntity(BigDecimal.valueOf(25), account);
        accountManagementService.withdrawMoney(withdrawTransaction);

        BigDecimal balance = accountManagementService.getBalance(account.getAccountId());

        assertEquals(BigInteger.valueOf(25), balance.toBigInteger());
    }

}

