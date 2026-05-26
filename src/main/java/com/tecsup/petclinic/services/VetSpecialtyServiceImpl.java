package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetSpecialtyRepository vetSpecialtyRepository;

    public VetSpecialtyServiceImpl(VetRepository vetRepository,
                                   SpecialtyRepository specialtyRepository,
                                   VetSpecialtyRepository vetSpecialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
        this.vetSpecialtyRepository = vetSpecialtyRepository;
    }

    @Override
    public VetSpecialty assignSpecialtyToVet(int vetId, int specialtyId)
            throws VetNotFoundException, SpecialtyNotFoundException, VetSpecialtyAlreadyExistsException {

        if (!vetRepository.existsById(vetId)) {
            throw new VetNotFoundException("Vet not found with id: " + vetId);
        }

        if (!specialtyRepository.existsById(specialtyId)) {
            throw new SpecialtyNotFoundException("Specialty not found with id: " + specialtyId);
        }

        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);

        if (vetSpecialtyRepository.existsById(id)) {
            throw new VetSpecialtyAlreadyExistsException(
                    "Specialty " + specialtyId + " is already assigned to vet " + vetId);
        }

        VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setId(id);

        log.info("Assigning specialty {} to vet {}", specialtyId, vetId);
        return vetSpecialtyRepository.save(vetSpecialty);
    }

    @Override
    public void removeSpecialtyFromVet(int vetId, int specialtyId)
            throws VetNotFoundException, SpecialtyNotFoundException {

        if (!vetRepository.existsById(vetId)) {
            throw new VetNotFoundException("Vet not found with id: " + vetId);
        }

        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);

        Optional<VetSpecialty> vetSpecialtyOpt = vetSpecialtyRepository.findById(id);
        if (!vetSpecialtyOpt.isPresent()) {
            throw new SpecialtyNotFoundException(
                    "Specialty " + specialtyId + " is not assigned to vet " + vetId);
        }

        log.info("Removing specialty {} from vet {}", specialtyId, vetId);
        vetSpecialtyRepository.delete(vetSpecialtyOpt.get());
    }
}
