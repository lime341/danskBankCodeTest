package com.codetest.danskebankcodetest.repositories;

import com.codetest.danskebankcodetest.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountManagementRepository extends JpaRepository<AccountEntity, UUID> {
}
