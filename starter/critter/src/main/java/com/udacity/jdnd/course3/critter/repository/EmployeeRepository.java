package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface  EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select e\n" +
            "from Employee e\n" +
            "where :dayAvailable MEMBER OF e.daysAvailable\n" +
            "group by e.id")
    List<Employee> findEmployeeByDaysAvailable(@Param("dayAvailable")DayOfWeek dayAvailable);
}
