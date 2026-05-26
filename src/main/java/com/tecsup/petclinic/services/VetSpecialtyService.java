package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VetSpecialtyService {

    // Integrante A
    VetSpecialty assignSpecialtyToVet(Integer vetId, Integer specialtyId);
    void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    // Integrante B
    List<VetSpecialty> findSpecialtiesByVet(Integer vetId);
    List<VetSpecialty> findVetsBySpecialty(Integer specialtyId);

    // Integrante C
    VetSpecialty setPrimarySpecialty(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    // Integrante D
    List<VetSpecialty> findVetsByMinExperience(Integer specialtyId, Integer minYears);
    VetSpecialty updateYearsExperience(Integer vetId, Integer specialtyId, Integer years) throws VetSpecialtyNotFoundException;
    VetSpecialty updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate date) throws VetSpecialtyNotFoundException;
}