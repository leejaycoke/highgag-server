package com.highgag.core.entity;

import com.highgag.core.domain.Role;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
public class User {

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

}
