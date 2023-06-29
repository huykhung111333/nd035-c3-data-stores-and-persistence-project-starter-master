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
import java.util.ArrayList;
import java.util.LinkedList;
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
            List<Pet> petsOfCus = petRepository.findAllById(customerDTO.getPetIds());
            customer.setPets(petsOfCus);
        }

        customer = customerRepository.save(customer);

        List<Long> idOfPet = customer.getPets()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());

        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(),
                customer.getNotes(), idOfPet);
    }

    public List<CustomerDTO> getAllCus() {
        List<CustomerDTO> resp = new LinkedList<>();
        List<Customer> customers = customerRepository.findAll();
        resp = customers.stream().map(el -> {
            List<Long> petId = new ArrayList<>();
            if (el.getPets() != null && !el.getPets().isEmpty()){
                petId = el.getPets().stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());
            }
            return new CustomerDTO(el.getId(), el.getName(), el.getPhoneNumber(), el.getNotes(), petId);}).collect(Collectors.toList());
        return resp;
    }

    public Customer getCustomerById(Long id){
        return customerRepository.getOne(id);
    }
    public CustomerDTO getOwnerByPet(long petId){
        Customer customer = customerRepository.getOwnerByPet(petId);
        List<Long> id = new ArrayList<>();
        if (customer.getPets() != null && !customer.getPets().isEmpty()){
            id = customer.getPets().stream()
                    .map(p -> p.getId())
                    .collect(Collectors.toList());
        }
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), id);
    }




}
