package com.codetest.danskebankcodetest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {

    @Id
    @EqualsAndHashCode.Include
    @Builder.Default
    private UUID transactionKey = UUID.randomUUID();

    @Column
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountEntity account;

    public TransactionEntity(BigDecimal amount, AccountEntity account) {
        this.amount = amount;
        this.account = account;
    }

}
