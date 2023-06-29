package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "select s from Schedule s inner join s.petList petList WHERE petList.id = :petId")
    List<Schedule> getSchedulesByPetId(@Param("petId")Long petId);

    @Query("select s from Schedule s inner join s.employees se where se.id = :employeeID")
    List<Schedule> getSchedulesByEmployeeId(@Param("employeeID") Long employeeID);

    @Query("SELECT s FROM Schedule s join s.pet p WHERE p.id IN :petIds")
    List<Schedule> getScheduleByCusIds(@Param("petIds") List<Long> petIds);
}