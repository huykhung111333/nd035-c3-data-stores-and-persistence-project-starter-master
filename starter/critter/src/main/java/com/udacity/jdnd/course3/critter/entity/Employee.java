package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Data
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @JoinTable(name = "employee_skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @JoinTable(name = "employee_days_available")
    private Set<DayOfWeek> daysAvailable;
}
