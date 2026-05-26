package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {

    // Buscar veterinarios por especialidad
    @Query("SELECT v FROM vets v " +
            "JOIN vet_specialties vs ON v.id = vs.vetId " +
            "WHERE vs.specialtyId = :specialtyId")
    List<Vet> findBySpecialtyId(@Param("specialtyId") int specialtyId);
}
