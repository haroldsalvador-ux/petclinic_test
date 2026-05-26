package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetServiceImpl(VetRepository vetRepository,
                          SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    /**
     * Retorna la lista de especialidades asignadas a un veterinario.
     * Si el veterinario no tiene especialidades, retorna lista vacía.
     */
    @Override
    public List<Specialty> findSpecialtiesByVet(int vetId) {
        List<Specialty> specialties = specialtyRepository.findByVetId(vetId);
        specialties.forEach(s -> log.info("Specialty: {}", s));
        return specialties;
    }

    /**
     * Retorna la lista de veterinarios que tienen una especialidad dada.
     */
    @Override
    public List<Vet> findVetsBySpecialty(int specialtyId) {
        List<Vet> vets = vetRepository.findBySpecialtyId(specialtyId);
        vets.forEach(v -> log.info("Vet: {}", v));
        return vets;
    }
}