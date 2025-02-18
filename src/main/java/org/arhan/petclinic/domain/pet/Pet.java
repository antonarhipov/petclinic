package org.arhan.petclinic.domain.pet;

import org.arhan.petclinic.domain.owner.OwnerId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pet is an aggregate root entity representing a pet in the veterinary clinic.
 * It contains all the essential information about a pet and maintains its medical history.
 */
public class Pet {
    private final PetId id;
    private final PetName name;
    private final Species species;
    private final LocalDate birthDate;
    private final OwnerId ownerId;
    private final List<MedicalRecord> medicalHistory;

    private Pet(PetId id, PetName name, Species species, LocalDate birthDate, OwnerId ownerId) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
        this.ownerId = ownerId;
        this.medicalHistory = new ArrayList<>();
    }

    /**
     * Creates a new Pet instance with the given attributes.
     *
     * @param id the unique identifier of the pet
     * @param name the name of the pet
     * @param species the species of the pet
     * @param birthDate the birth date of the pet
     * @param ownerId the ID of the pet's owner
     * @return a new Pet instance
     * @throws IllegalArgumentException if any parameter is null or if birthDate is in the future
     */
    public static Pet create(PetId id, PetName name, Species species, LocalDate birthDate, OwnerId ownerId) {
        validateCreation(id, name, species, birthDate, ownerId);
        return new Pet(id, name, species, birthDate, ownerId);
    }

    private static void validateCreation(PetId id, PetName name, Species species, LocalDate birthDate, OwnerId ownerId) {
        if (id == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Pet name cannot be null");
        }
        if (species == null) {
            throw new IllegalArgumentException("Species cannot be null");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }

    public PetId getId() {
        return id;
    }

    public PetName getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public List<MedicalRecord> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }
}