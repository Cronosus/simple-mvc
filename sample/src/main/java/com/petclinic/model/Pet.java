package com.petclinic.model;

public class Pet {
    public String getId() {
        return id;
    }

    private final String id;

    public Pet(String id) {
        this.id = id;
    }
}
