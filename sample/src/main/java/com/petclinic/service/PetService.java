package com.petclinic.service;

import com.petclinic.model.Pet;

public interface PetService {
    public Pet create (Pet pet);

    Pet get(Long id);
}
