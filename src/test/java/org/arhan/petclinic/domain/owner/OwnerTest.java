package org.arhan.petclinic.domain.owner;

import org.arhan.petclinic.domain.pet.PetId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
    
    private static final FullName VALID_NAME = new FullName("John", "Doe");
    private static final Address VALID_ADDRESS = new Address(
        "123 Main St",
        "Springfield",
        "IL",
        "62701"
    );
    private static final ContactInformation VALID_CONTACT = new ContactInformation(
        "john.doe@example.com",
        "+12345678901",
        VALID_ADDRESS
    );

    @Test
    void shouldCreateValidOwner() {
        // Given
        OwnerId id = OwnerId.generate();
        
        // When
        Owner owner = Owner.create(id, VALID_NAME, VALID_CONTACT);
        
        // Then
        assertEquals(id, owner.getId());
        assertEquals(VALID_NAME, owner.getName());
        assertEquals(VALID_CONTACT, owner.getContactInfo());
        assertTrue(owner.getPets().isEmpty());
    }
    
    @Test
    void shouldNotCreateOwnerWithNullId() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> Owner.create(null, VALID_NAME, VALID_CONTACT));
    }
    
    @Test
    void shouldNotCreateOwnerWithNullName() {
        // Given
        OwnerId id = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> Owner.create(id, null, VALID_CONTACT));
    }
    
    @Test
    void shouldNotCreateOwnerWithNullContactInfo() {
        // Given
        OwnerId id = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> Owner.create(id, VALID_NAME, null));
    }
    
    @Test
    void shouldAddPetToOwner() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        PetId petId = PetId.generate();
        
        // When
        owner.addPet(petId);
        
        // Then
        assertTrue(owner.getPets().contains(petId));
        assertEquals(1, owner.getPets().size());
    }
    
    @Test
    void shouldNotAddNullPet() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> owner.addPet(null));
    }
    
    @Test
    void shouldNotAddDuplicatePet() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        PetId petId = PetId.generate();
        
        // When
        owner.addPet(petId);
        
        // Then
        assertThrows(IllegalStateException.class,
            () -> owner.addPet(petId));
    }
    
    @Test
    void shouldRemovePetFromOwner() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        PetId petId = PetId.generate();
        owner.addPet(petId);
        
        // When
        owner.removePet(petId);
        
        // Then
        assertFalse(owner.getPets().contains(petId));
        assertTrue(owner.getPets().isEmpty());
    }
    
    @Test
    void shouldNotRemoveNonExistentPet() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        PetId petId = PetId.generate();
        
        // When/Then
        assertThrows(IllegalStateException.class,
            () -> owner.removePet(petId));
    }
}