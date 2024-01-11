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
public class TransactionExterne {
    @GeneratedValue
    @Id
    private Long id;

    @Column
    String type;

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    BigDecimal montant;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    User user;
}
