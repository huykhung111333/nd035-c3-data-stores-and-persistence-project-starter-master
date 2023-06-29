package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets;
}