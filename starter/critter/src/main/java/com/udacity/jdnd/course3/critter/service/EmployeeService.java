package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        EmployeeDTO resp = new EmployeeDTO();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());

        employee = employeeRepository.save(employee);
        resp.setId(employee.getId());
        resp.setName(employee.getName());
        resp.setSkills(employee.getSkills());
        resp.setDaysAvailable(employee.getDaysAvailable());

        return resp;
    }

    public EmployeeDTO getEmployeeById(Long id){
        EmployeeDTO resp = new EmployeeDTO();
        Employee employee = employeeRepository.getOne(id);

        resp.setId(employee.getId());
        resp.setName(employee.getName());
        resp.setSkills(employee.getSkills());
        resp.setDaysAvailable(employee.getDaysAvailable());
        return resp;
    }

//    public List<Employee> getEmployeesByIDs(List<Long> ids) {
//        return employeeRepository.findAllById(ids);
//    }

//    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeRequestDTO) {
//        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
//        List<Employee> employeeList = employeeRepository.findEmployeeByDaysAvailable(dayOfWeek);
//
//        return employeeList.stream()
//                .filter(employee -> hasAllSkills(employee, employeeRequestDTO.getSkills()))
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//    }
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        Set<DayOfWeek> utpDaysAvailable = new HashSet<>();
        if (employee.getDaysAvailable() != null && !employee.getDaysAvailable().isEmpty()){
            utpDaysAvailable.addAll(employee.getDaysAvailable());
        }
        utpDaysAvailable.addAll(daysAvailable);
        employee.setDaysAvailable(utpDaysAvailable);
        employeeRepository.save(employee);
    }
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeRepository.findEmployeesForService(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills(), employeeDTO.getSkills().size());
        List<EmployeeDTO> resp = employees.stream().map(el ->{
            EmployeeDTO e = new EmployeeDTO();
            e.setId(el.getId());
            e.setName(el.getName());
            e.setSkills(el.getSkills());
            e.setDaysAvailable(el.getDaysAvailable());
            return e;
        }).collect(Collectors.toList());
        return resp;
    }

}