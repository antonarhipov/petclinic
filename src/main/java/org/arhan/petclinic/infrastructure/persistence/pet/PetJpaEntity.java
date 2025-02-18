package org.arhan.petclinic.infrastructure.persistence.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.pet.*;
import java.time.LocalDate;

/**
 * JPA entity for persisting Pet aggregate root.
 */
@Entity
@Table(name = "pets")
public class PetJpaEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String species;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    protected PetJpaEntity() {
        // Required by JPA
    }

    public static PetJpaEntity fromDomain(Pet pet) {
        var entity = new PetJpaEntity();
        entity.id = pet.getId().value();
        entity.name = pet.getName().value();
        entity.species = pet.getSpecies().name();
        entity.birthDate = pet.getBirthDate();
        entity.ownerId = pet.getOwnerId().value();
        return entity;
    }

    public Pet toDomain() {
        return Pet.create(
            PetId.fromString(id),
            new PetName(name),
            new Species(species),
            birthDate,
            OwnerId.fromString(ownerId)
        );
    }

    // Getters and setters required by JPA
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}