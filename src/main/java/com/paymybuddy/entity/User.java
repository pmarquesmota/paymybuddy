package com.paymybuddy.entity;

import lombok.*;
import org.hibernate.annotations.Type;

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

    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    BigDecimal solde;

    @JoinTable(
            name = "User_to_user",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "ami"))
    @ManyToMany(cascade = CascadeType.REMOVE)
    List<User> amis;

    public User(String name, String password, String iban, BigDecimal solde, List<User> amis) {
        this.name = name;
        this.password = password;
        this.iban = iban;
        this.solde = solde;
        this.amis = amis;
    }
}
