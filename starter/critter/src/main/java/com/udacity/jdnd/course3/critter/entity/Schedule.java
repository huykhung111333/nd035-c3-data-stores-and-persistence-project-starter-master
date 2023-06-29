package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @ManyToMany
    @JoinTable(
            name = "schedule_employee",
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "schedule_pet",
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> petList;

    @ElementCollection
    @JoinTable(name = "schedule_skill")
    private Set<EmployeeSkill> setEmployeeSkill;

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
