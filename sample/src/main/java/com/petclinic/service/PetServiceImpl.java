package com.petclinic.service;

import com.petclinic.model.Pet;
import com.thoughtworks.di.annotation.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PetServiceImpl implements PetService {

    private static AtomicLong idSequence = new AtomicLong();
    private Map<Long, Pet> pets = new HashMap<>();

    @Override
    public Pet create(Pet pet) {
        pet.setId(idSequence.incrementAndGet());
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet get(Long id) {
        return pets.get(id);
    }
}
