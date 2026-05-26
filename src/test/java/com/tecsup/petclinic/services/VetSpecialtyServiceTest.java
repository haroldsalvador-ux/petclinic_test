package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    /**
     * Vet 1 (James Carter) no tiene especialidades en data.sql.
     * Se asigna specialty 2 (surgery) y se verifica el resultado.
     */
    @Test
    public void testAssignSpecialtyToVet() {

        int VET_ID       = 1;
        int SPECIALTY_ID = 2;

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

        // Cleanup: quitar la specialty asignada para no afectar otros tests
        try {
            vetSpecialtyService.removeSpecialtyFromVet(VET_ID, SPECIALTY_ID);
        } catch (Exception e) {
            fail("Cleanup failed: " + e.getMessage());
        }
    }

    /**
     * Primero se asigna specialty 3 (dentistry) a vet 1, luego se quita.
     * El test es autocontenido: no depende de datos preexistentes.
     */
    @Test
    public void testRemoveSpecialtyFromVet() {

        int VET_ID       = 1;
        int SPECIALTY_ID = 3;

        // Setup: asignar primero
        try {
            vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        // Quitar la especialidad
        try {
            vetSpecialtyService.removeSpecialtyFromVet(VET_ID, SPECIALTY_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        log.info("Removed specialty {} from vet {} successfully", SPECIALTY_ID, VET_ID);
    }

    /**
     * Vet 2 (Helen Leary) ya tiene specialty 1 (radiology) en data.sql.
     * Intentar asignarla de nuevo debe lanzar VetSpecialtyAlreadyExistsException.
     */
    @Test
    public void testAssignDuplicate_ShouldFail() {

        int VET_ID       = 2;
        int SPECIALTY_ID = 1;

        assertThrows(VetSpecialtyAlreadyExistsException.class, () ->
                vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID)
        );

        log.info("Duplicate assignment correctly rejected for vet {} - specialty {}", VET_ID, SPECIALTY_ID);
    }
}
