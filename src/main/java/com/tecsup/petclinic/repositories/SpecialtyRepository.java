package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    // Buscar especialidades por veterinario
    @Query("SELECT s FROM specialties s " +
            "JOIN vet_specialties vs ON s.id = vs.specialtyId " +
            "WHERE vs.vetId = :vetId")
    List<Specialty> findByVetId(@Param("vetId") int vetId);
}
