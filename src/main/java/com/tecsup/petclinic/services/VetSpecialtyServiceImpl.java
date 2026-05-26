package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;

    public VetSpecialtyServiceImpl(VetSpecialtyRepository vetSpecialtyRepository) {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
    }

    @Override
    public VetSpecialty assignSpecialtyToVet(Integer vetId, Integer specialtyId) {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        if (vetSpecialtyRepository.existsById(id)) {
            throw new IllegalArgumentException(
                "La especialidad " + specialtyId + " ya está asignada al veterinario " + vetId);
        }
        VetSpecialty vs = new VetSpecialty(id, null, 0, false, null);
        return vetSpecialtyRepository.save(vs);
    }

    @Override
    public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId)
            throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty vs = vetSpecialtyRepository.findById(id)
            .orElseThrow(() -> new VetSpecialtyNotFoundException(
                "No existe la relación vet=" + vetId + " specialty=" + specialtyId));
        vetSpecialtyRepository.delete(vs);
    }

    @Override
    public List<VetSpecialty> findSpecialtiesByVet(Integer vetId) {
        return vetSpecialtyRepository.findByVetId(vetId);
    }

    @Override
    public List<VetSpecialty> findVetsBySpecialty(Integer specialtyId) {
        return vetSpecialtyRepository.findBySpecialtyId(specialtyId);
    }

    // ───── TU MÉTODO (Integrante C) ─────
    @Override
    @Transactional
    public VetSpecialty setPrimarySpecialty(Integer vetId, Integer specialtyId)
            throws VetSpecialtyNotFoundException {

        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);

        // 1. Verifica que la especialidad esté asignada al vet
        VetSpecialty vs = vetSpecialtyRepository.findById(id)
            .orElseThrow(() -> new VetSpecialtyNotFoundException(
                "La especialidad " + specialtyId
                + " no está asignada al veterinario " + vetId));

        // 2. Quita la bandera principal a TODAS las especialidades del vet
        vetSpecialtyRepository.clearPrimaryForVet(vetId);

        // 3. Marca solo esta como principal
        vs.setIsPrimary(true);
        return vetSpecialtyRepository.save(vs);
    }

    @Override
    public List<VetSpecialty> findVetsByMinExperience(Integer specialtyId, Integer minYears) {
        return vetSpecialtyRepository.findBySpecialtyIdAndMinExperience(specialtyId, minYears);
    }

    @Override
    public VetSpecialty updateYearsExperience(Integer vetId, Integer specialtyId, Integer years)
            throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty vs = vetSpecialtyRepository.findById(id)
            .orElseThrow(() -> new VetSpecialtyNotFoundException(
                "No existe la relación vet=" + vetId + " specialty=" + specialtyId));
        vs.setYearsExperience(years);
        return vetSpecialtyRepository.save(vs);
    }

    @Override
    public VetSpecialty updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate date)
            throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty vs = vetSpecialtyRepository.findById(id)
            .orElseThrow(() -> new VetSpecialtyNotFoundException(
                "No existe la relación vet=" + vetId + " specialty=" + specialtyId));
        vs.setCertificationDate(date);
        return vetSpecialtyRepository.save(vs);
    }
}