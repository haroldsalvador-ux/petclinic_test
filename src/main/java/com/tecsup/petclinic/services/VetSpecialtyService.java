package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;

public interface VetSpecialtyService {

    VetSpecialty assignSpecialtyToVet(int vetId, int specialtyId)
            throws VetNotFoundException, SpecialtyNotFoundException, VetSpecialtyAlreadyExistsException;

    void removeSpecialtyFromVet(int vetId, int specialtyId)
            throws VetNotFoundException, SpecialtyNotFoundException;
}
