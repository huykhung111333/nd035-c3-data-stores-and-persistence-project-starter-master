package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private PetType petType;

    private LocalDate birthDate;

    private String notes;

    /*
    Declares the Pet end of the "one to many" relationship with Customer

    Adds customer_id as a foreign key in Pet
    which references the id in Customer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
