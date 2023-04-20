package com.paymybuddy.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    String name;

    @Column
    String password;

    @Column
    String iban;

    @Column(columnDefinition = "Decimal(10,2)")
    Double solde;

    @JoinTable(
            name = "User_to_user",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "ami"))
    @ManyToMany
    List<User> amis;

    public User(String name, String password, String iban, Double solde) {
        this.name = name;
        this.password = password;
        this.iban = iban;
        this.solde = solde;
    }
}
