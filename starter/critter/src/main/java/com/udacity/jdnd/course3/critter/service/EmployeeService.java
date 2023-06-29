package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        Employee tempEmployee = employeeRepository.save(mapDtoToEntity(employeeDTO));
        return mapEntityToDto(tempEmployee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.getOne(id);
    }

    public List<Employee> getEmployeesByIDs(List<Long> ids) {
        return employeeRepository.findAllById(ids);
    }

    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
        List<Employee> employeeList = employeeRepository.findEmployeeByDaysAvailable(dayOfWeek);

        return employeeList.stream()
                .filter(employee -> hasAllSkills(employee, employeeRequestDTO.getSkills()))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private boolean hasAllSkills(Employee employee, Set<EmployeeSkill> skills) {
        return employee.getSkills().containsAll(skills);
    }
    private Employee mapDtoToEntity(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }

    private EmployeeDTO mapEntityToDto(Employee employee) {
        EmployeeDTO employeeDto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }
}