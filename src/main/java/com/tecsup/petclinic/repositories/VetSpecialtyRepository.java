package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, VetSpecialtyId> {

    @Query("SELECT vs FROM vet_specialties vs WHERE vs.id.vetId = :vetId")
    List<VetSpecialty> findByVetId(@Param("vetId") Integer vetId);

    @Query("SELECT vs FROM vet_specialties vs WHERE vs.id.specialtyId = :specialtyId")
    List<VetSpecialty> findBySpecialtyId(@Param("specialtyId") Integer specialtyId);

    @Query("SELECT vs FROM vet_specialties vs WHERE vs.id.specialtyId = :specialtyId AND vs.yearsExperience >= :minYears")
    List<VetSpecialty> findBySpecialtyIdAndMinExperience(
            @Param("specialtyId") Integer specialtyId,
            @Param("minYears") Integer minYears);

    @Modifying
    @Query("UPDATE vet_specialties vs SET vs.isPrimary = false WHERE vs.id.vetId = :vetId")
    void clearPrimaryForVet(@Param("vetId") Integer vetId);
}