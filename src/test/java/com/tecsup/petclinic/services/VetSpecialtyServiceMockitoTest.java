package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Slf4j
@SpringBootTest
public class VetSpecialtyServiceMockitoTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @MockitoBean
    private VetRepository vetRepository;

    @MockitoBean
    private SpecialtyRepository specialtyRepository;

    @MockitoBean
    private VetSpecialtyRepository vetSpecialtyRepository;

    /**
     * Simula que el vet y la specialty existen y que la relación aún no está asignada.
     * Verifica que save() se invoca y que el resultado tiene los IDs correctos.
     */
    @Test
    public void testAssignSpecialtyToVet() {

        int VET_ID       = 1;
        int SPECIALTY_ID = 2;

        VetSpecialtyId id = new VetSpecialtyId(VET_ID, SPECIALTY_ID);

        VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setId(id);

        Mockito.when(vetRepository.existsById(VET_ID)).thenReturn(true);
        Mockito.when(specialtyRepository.existsById(SPECIALTY_ID)).thenReturn(true);
        Mockito.when(vetSpecialtyRepository.existsById(id)).thenReturn(false);
        Mockito.when(vetSpecialtyRepository.save(vetSpecialty)).thenReturn(vetSpecialty);

        VetSpecialty result = null;

        try {
            result = vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        log.info("Assigned: {}", result);

        assertNotNull(result);
        assertEquals(VET_ID,       result.getId().getVetId());
        assertEquals(SPECIALTY_ID, result.getId().getSpecialtyId());
    }

    /**
     * Simula que la relación vet-specialty existe.
     * Verifica que delete() se invoca correctamente y no lanza excepción.
     */
    @Test
    public void testRemoveSpecialtyFromVet() {

        int VET_ID       = 2;
        int SPECIALTY_ID = 1;

        VetSpecialtyId id       = new VetSpecialtyId(VET_ID, SPECIALTY_ID);
        VetSpecialty existing   = new VetSpecialty(id, null, null, null, null);

        Mockito.when(vetRepository.existsById(VET_ID)).thenReturn(true);
        Mockito.when(vetSpecialtyRepository.findById(id)).thenReturn(Optional.of(existing));
        Mockito.doNothing().when(vetSpecialtyRepository).delete(existing);

        try {
            vetSpecialtyService.removeSpecialtyFromVet(VET_ID, SPECIALTY_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        log.info("Removed specialty {} from vet {}", SPECIALTY_ID, VET_ID);

        // Confirma que delete fue realmente invocado
        Mockito.verify(vetSpecialtyRepository).delete(existing);
    }

    /**
     * Simula que la relación ya existe (existsById devuelve true).
     * Verifica que se lanza VetSpecialtyAlreadyExistsException.
     */
    @Test
    public void testAssignDuplicate_ShouldFail() {

        int VET_ID       = 2;
        int SPECIALTY_ID = 1;

        VetSpecialtyId id = new VetSpecialtyId(VET_ID, SPECIALTY_ID);

        Mockito.when(vetRepository.existsById(VET_ID)).thenReturn(true);
        Mockito.when(specialtyRepository.existsById(SPECIALTY_ID)).thenReturn(true);
        Mockito.when(vetSpecialtyRepository.existsById(id)).thenReturn(true); // ya asignada

        assertThrows(VetSpecialtyAlreadyExistsException.class, () ->
                vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID)
        );

        log.info("Duplicate assignment correctly rejected for vet {} - specialty {}", VET_ID, SPECIALTY_ID);
    }
}
