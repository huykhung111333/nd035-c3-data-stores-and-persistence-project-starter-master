package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface  EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select e\n" +
            "from Employee e\n" +
            "where :dayAvailable MEMBER OF e.daysAvailable\n" +
            "group by e.id")
    List<Employee> findEmployeeByDaysAvailable(@Param("dayAvailable")DayOfWeek dayAvailable);

    @Query("SELECT e " +
            "FROM Employee e join e.daysAvailable da join e.skills s " +
            "WHERE s IN :skills " +
            " and da = :daysAvailable " +
            "GROUP BY e " +
            "HAVING COUNT(s) = :skillsCount")
    List<Employee> findEmployeesForService(@Param("daysAvailable") DayOfWeek daysAvailable, @Param("skills") Set<EmployeeSkill> skills,
                                           @Param("skillsCount") long skillsCount);
}
