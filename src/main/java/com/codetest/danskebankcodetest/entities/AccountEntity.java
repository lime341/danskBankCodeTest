package com.codetest.danskebankcodetest.entities;

import com.danskebankcodetest.model.AccountDTO;
import com.danskebankcodetest.model.AccountInput;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class AccountEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accountId;

    @Column
    private String customerId;

    @Column
    private String name;

    @Column
    private Currency currencyCode;

    @Column
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account")
    private List<TransactionEntity> transactions;

    public static AccountEntity fromInputDto(AccountInput playerInput) {
        return AccountEntity
                .builder()
                .accountId(UUID.randomUUID())
                .name(playerInput.getName())
                .customerId(playerInput.getCustomerId())
                .balance(BigDecimal.ZERO)
                .currencyCode(Currency.getInstance(Locale.getDefault()))
                .transactions(new ArrayList<>())
                .build();
    }

    public AccountDTO toDto() {
        return new AccountDTO().id(accountId.toString());
    }
}
