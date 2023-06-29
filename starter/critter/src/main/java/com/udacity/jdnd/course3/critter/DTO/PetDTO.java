package com.udacity.jdnd.course3.critter.DTO;

import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.*;

import javax.persistence.JoinTable;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private long id;

    @JoinTable(name = "pet_type")
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;

}
