package com.udacity.jdnd.course3.critter.DTO;


import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;
    private LocalDate date;

}
