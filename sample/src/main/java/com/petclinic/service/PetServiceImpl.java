package com.petclinic.service;

import com.petclinic.model.Pet;
import com.thoughtworks.di.annotation.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PetServiceImpl implements PetService {

    private static AtomicLong idSequence = new AtomicLong();
    private Map<String, Pet> pets = new HashMap<>();

    @Override
    public Pet create(Pet pet) {
        pet.setId(String.valueOf(idSequence.incrementAndGet()));
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet get(String id) {
        Pet pet = pets.get(id);
        if (pet == null){
            pet = new Pet("Dummy", "Doudou");
        }
        return pet;
    }
}
