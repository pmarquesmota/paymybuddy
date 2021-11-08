package com.paymybuddy.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInterne {
    @Id
    private Long id;

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    BigDecimal amount;

    @Column
    User crediteur;

    @Column
    User debiteur;

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    BigDecimal commission;
}