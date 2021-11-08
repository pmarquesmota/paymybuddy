package com.paymybuddy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;

    @Column
    String name;

    @Column
    String password;

    @Column
    String iban;

    @JoinTable(
            name = "User_to_usaer",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "ami"))
    @ManyToMany
    List<User> amis;
}
