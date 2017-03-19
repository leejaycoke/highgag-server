package com.highgag.core.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

}
