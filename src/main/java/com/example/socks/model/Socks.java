package com.example.socks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "socks")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color")
    @NotNull
    @Size(min = 3, message = "Color should be at least 3 characters")
    private String color;

    @Column(name = "cotton_part")
    @Min(value = 0, message = "Cotton part should be at least 0 percent")
    @Max(value = 100, message = "Cotton part should be no more 100 percent")
    private int cottonPart;

    @Column(name = "quantity")
    @Min(value = 1, message = "Quantity should be at least 1 pair")
    private int quantity;

}
