package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;
    @Autowired
    PetService petService;
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getId() != 0) {
            return new CustomerDTO();
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if (customerDTO.getPetIds() != null && !customerDTO.getPetIds().isEmpty()) {
            List<Pet> petsOfCus = this.petRepository.findAllById(customerDTO.getPetIds());
            customer.setPets(petsOfCus);
        }

        customer = this.customerRepository.save(customer);

        List<Long> idOfPet = customer.getPets()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());

        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(),
                customer.getNotes(), idOfPet);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.getOne(id);
    }



}
