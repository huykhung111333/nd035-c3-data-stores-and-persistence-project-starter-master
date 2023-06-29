package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public PetDTO savePet(PetDTO petDTO) {
        if (petDTO.getId() != 0) {
            return new PetDTO();
        }

        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setPetType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getName());

        Optional<Customer> customerOptional = this.customerRepository.findById(petDTO.getOwnerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Pet> pets = customer.getPets();
            if (pets == null) {
                pets = new ArrayList<>();
            }
            pets.add(pet);
            customer.setPets(pets);
            customerRepository.save(customer);
            pet.setCustomer(customer);
        }

        pet = this.petRepository.save(pet);

        Long customerId = pet.getCustomer() != null ? pet.getCustomer().getId() : 0L;
        return new PetDTO(pet.getId(), pet.getPetType(), pet.getName(), customerId, pet.getBirthDate(), pet.getNotes());
    }

    public PetDTO getPetById(Long id) {
        return this.petRepository.findById(id)
                .map(pet -> new PetDTO(pet.getId(), pet.getPetType(), pet.getName(), pet.getCustomer().getId(),
                        pet.getBirthDate(), pet.getNotes()))
                .orElse(new PetDTO());
    }

    public List<PetDTO> getPetOfCus(Long id){
        List<Pet> pets = this.petRepository.getPetOwnerId(id);
        List<PetDTO> petDTO = new ArrayList<>();
        if (pets != null && !pets.isEmpty()){
            pets.stream().map(pet ->
                            petDTO.add(new PetDTO(pet.getId(), pet.getPetType(), pet.getName(), id, pet.getBirthDate(), pet.getNotes())))
                    .collect(Collectors.toList());
        }
        return petDTO;
    }
    public List<PetDTO> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        return petList.stream()
                .map(pet -> {
                    PetDTO petDTO = new PetDTO();
                    BeanUtils.copyProperties(pet, petDTO, "customer", "petType");
                    Customer customer = customerRepository.getOne(pet.getCustomer().getId());
                    petDTO.setOwnerId(customer.getId());
                    petDTO.setType(pet.getPetType());
                    return petDTO;
                })
                .collect(Collectors.toList());
    }



}
