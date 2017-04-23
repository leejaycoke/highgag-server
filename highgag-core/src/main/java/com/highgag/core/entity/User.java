package com.highgag.core.entity;

import com.highgag.core.domain.Role;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(nullable = false, length = 20)
    private String account;

    @Column(nullable = false, length = 12)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {}

    @Builder
    public User(String account, String name, String email, String password, Role role) {
        this.account = account;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
