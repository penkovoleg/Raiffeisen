package com.example.socks.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "socks")
@Data
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "cotton_part")
    private int cottonPart;

    @Column(name = "quantity")
    private int quantity;

}
