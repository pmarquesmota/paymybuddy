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
public class TransactionExterne {
    @Id
    private Long id;

    @Column
    String type;

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    BigDecimal montant;

    @Column
    User user;
}
