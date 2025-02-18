package org.arhan.petclinic.domain.owner;

import org.arhan.petclinic.domain.pet.PetId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Owner is an aggregate root entity representing a pet owner in the veterinary clinic.
 * It contains all the essential information about the owner and maintains the list of their pets.
 */
public class Owner {
    private final OwnerId id;
    private final FullName name;
    private final ContactInformation contactInfo;
    private final List<PetId> pets;

    private Owner(OwnerId id, FullName name, ContactInformation contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.pets = new ArrayList<>();
    }

    /**
     * Creates a new Owner instance.
     *
     * @param id the unique identifier of the owner
     * @param name the full name of the owner
     * @param contactInfo the contact information of the owner
     * @return a new Owner instance
     * @throws IllegalArgumentException if any parameter is null
     */
    public static Owner create(OwnerId id, FullName name, ContactInformation contactInfo) {
        validateCreation(id, name, contactInfo);
        return new Owner(id, name, contactInfo);
    }

    private static void validateCreation(OwnerId id, FullName name, ContactInformation contactInfo) {
        if (id == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact information cannot be null");
        }
    }

    /**
     * Adds a pet to this owner.
     *
     * @param petId the ID of the pet to add
     * @throws IllegalArgumentException if petId is null
     * @throws IllegalStateException if the pet is already owned by this owner
     */
    public void addPet(PetId petId) {
        if (petId == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        if (pets.contains(petId)) {
            throw new IllegalStateException("Pet is already owned by this owner");
        }
        pets.add(petId);
    }

    /**
     * Removes a pet from this owner.
     *
     * @param petId the ID of the pet to remove
     * @throws IllegalStateException if the pet is not owned by this owner
     */
    public void removePet(PetId petId) {
        if (!pets.contains(petId)) {
            throw new IllegalStateException("Pet is not owned by this owner");
        }
        pets.remove(petId);
    }

    public OwnerId getId() {
        return id;
    }

    public FullName getName() {
        return name;
    }

    public ContactInformation getContactInfo() {
        return contactInfo;
    }

    public List<PetId> getPets() {
        return Collections.unmodifiableList(pets);
    }
}