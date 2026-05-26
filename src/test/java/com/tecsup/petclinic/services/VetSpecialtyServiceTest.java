package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    @Transactional
    public void testSetPrimarySpecialty() {
        Integer VET_ID = 5;
        Integer SPECIALTY_ID = 1;
        VetSpecialty result = null;
        try {
            result = vetSpecialtyService.setPrimarySpecialty(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
        assertNotNull(result);
        assertTrue(result.getIsPrimary());
        assertEquals(VET_ID, result.getId().getVetId());
        assertEquals(SPECIALTY_ID, result.getId().getSpecialtyId());
    }

    @Test
    @Transactional
    public void testSetPrimarySpecialty_OnlyOnePrimary() {
        Integer VET_ID = 3;
        Integer NEW_PRIMARY = 3;
        Integer OLD_PRIMARY = 2;
        try {
            vetSpecialtyService.setPrimarySpecialty(VET_ID, NEW_PRIMARY);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
        List<VetSpecialty> specialties = vetSpecialtyService.findSpecialtiesByVet(VET_ID);
        long totalPrimary = specialties.stream().filter(vs -> Boolean.TRUE.equals(vs.getIsPrimary())).count();
        assertEquals(1, totalPrimary);
        VetSpecialty newPrimary = specialties.stream().filter(vs -> vs.getId().getSpecialtyId().equals(NEW_PRIMARY)).findFirst().orElse(null);
        assertNotNull(newPrimary);
        assertTrue(newPrimary.getIsPrimary());
        VetSpecialty oldPrimary = specialties.stream().filter(vs -> vs.getId().getSpecialtyId().equals(OLD_PRIMARY)).findFirst().orElse(null);
        assertNotNull(oldPrimary);
        assertFalse(oldPrimary.getIsPrimary());
    }

    @Test
    @Transactional
    public void testSetPrimarySpecialty_NotAssigned() {
        Integer VET_ID = 2;
        Integer SPECIALTY_ID = 99;
        assertThrows(VetSpecialtyNotFoundException.class, () -> vetSpecialtyService.setPrimarySpecialty(VET_ID, SPECIALTY_ID));
    }
}
