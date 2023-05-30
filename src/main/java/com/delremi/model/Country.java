package com.delremi.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
