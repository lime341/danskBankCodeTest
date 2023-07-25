package com.codetest.danskebankcodetest.services;

import com.codetest.danskebankcodetest.entities.AccountEntity;
import com.codetest.danskebankcodetest.entities.TransactionEntity;
import com.codetest.danskebankcodetest.repositories.AccountManagementRepository;
import com.danskebankcodetest.model.AccountDTO;
import com.danskebankcodetest.model.AccountInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountManagementService {
    private final AccountManagementRepository repo;

    public AccountDTO createAccount(AccountInput input) {
        return repo.save(AccountEntity.fromInputDto(input)).toDto();
    }

    public AccountEntity getAccount(UUID accountId){
        if (repo.findById(accountId).isPresent()){
            return repo.findById(accountId).get();
        }

        return null;
    }

    public void depositMoney(TransactionEntity transaction) {
        Optional<AccountEntity> optionalAccount = repo.findById(transaction.getAccount().getAccountId());
        if (optionalAccount.isPresent()){
            AccountEntity account = optionalAccount.get();
            account.setBalance(account.getBalance().add(transaction.getAmount()));
            repo.save(account);
        } else {
            throw new NullPointerException("Account could not be found");
        }
    }

    public void withdrawMoney(TransactionEntity transaction) {
        Optional<AccountEntity> optionalAccount = repo.findById(transaction.getAccount().getAccountId());
        if (optionalAccount.isPresent()){
            AccountEntity account = optionalAccount.get();
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
            repo.save(account);
        } else {
            throw new NullPointerException("Account could not be found");
        }
    }

    public BigDecimal getBalance(UUID accountID) {
        Optional<AccountEntity> optionalAccount = repo.findById(accountID);
        if (optionalAccount.isPresent()){
            AccountEntity account = optionalAccount.get();
            return account.getBalance();
        } else {
            throw new NullPointerException("Account could not be found");
        }
    }

}
