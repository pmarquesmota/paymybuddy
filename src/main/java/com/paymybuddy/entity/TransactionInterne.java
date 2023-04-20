package com.paymybuddy.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    Double montant;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    User crediteur;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    User debiteur;

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    Double commission;
}