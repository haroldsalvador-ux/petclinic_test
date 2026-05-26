package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;

import java.util.List;

public interface VetService {

    List<Specialty> findSpecialtiesByVet(int vetId);

    List<Vet> findVetsBySpecialty(int specialtyId);
}
