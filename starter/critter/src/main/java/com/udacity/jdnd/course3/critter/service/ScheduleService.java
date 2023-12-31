package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepos;
    @Autowired
    private PetRepository petRepos;
    @Autowired
    private EmployeeRepository employeeRepos;
    @Autowired
    private CustomerRepository customerRepos;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        ScheduleDTO resp = new ScheduleDTO();

        List<Pet> pets = petRepos.findAllById(scheduleDTO.getPetIds());
        List<Employee> employees = employeeRepos.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setPetList(pets);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setEmployees(employees);
        schedule = scheduleRepos.save(schedule);

        resp.setId(schedule.getId());
        resp.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        resp.setDate(schedule.getDate());
        resp.setActivities(schedule.getActivities());
        resp.setPetIds(schedule.getPetList().stream().map(Pet::getId).collect(Collectors.toList()));
        return resp;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS;
        List<Schedule> schedules = scheduleRepos.findAll();
        scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDate());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPetList().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<ScheduleDTO> scheduleDTOS;
        List<Schedule> schedules = scheduleRepos.getSchedulesByEmployeeId(employeeId);
        scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDate());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPetList().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        List<ScheduleDTO> scheduleDTOS;
        List<Schedule> schedules = scheduleRepos.getSchedulesByPetId(petId);
        scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDate());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPetList().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<ScheduleDTO> scheduleDTOS;
        Customer customer = this.customerRepos.getOne(customerId);
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        List<Schedule> schedules = scheduleRepos.getScheduleByCusIds(petIds);
        scheduleDTOS = schedules.stream().map(s -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(s.getId());
            scheduleDTO.setEmployeeIds(s.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setDate(s.getDate());
            scheduleDTO.setActivities(s.getActivities());
            scheduleDTO.setPetIds(s.getPetList().stream().map(Pet::getId).collect(Collectors.toList()));
            return scheduleDTO;
        }).collect(Collectors.toList());
        return scheduleDTOS;
    }
}