package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "vet_specialties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(VetSpecialtyId.class)
public class VetSpecialty {

    @Id
    @Column(name = "vet_id")
    private int vetId;

    @Id
    @Column(name = "specialty_id")
    private int specialtyId;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "years_experience")
    private int yearsExperience;

    @Column(name = "is_primary")
    private boolean isPrimary;

    private String notes;
}
