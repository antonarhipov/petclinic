package org.arhan.petclinic.domain.pet;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    
    @Test
    void shouldCreateValidPet() {
        // Given
        PetId id = PetId.generate();
        PetName name = new PetName("Max");
        Species species = new Species("Dog");
        LocalDate birthDate = LocalDate.now().minusYears(2);
        OwnerId ownerId = OwnerId.generate();
        
        // When
        Pet pet = Pet.create(id, name, species, birthDate, ownerId);
        
        // Then
        assertEquals(id, pet.getId());
        assertEquals(name, pet.getName());
        assertEquals(species, pet.getSpecies());
        assertEquals(birthDate, pet.getBirthDate());
        assertEquals(ownerId, pet.getOwnerId());
        assertTrue(pet.getMedicalHistory().isEmpty());
    }
    
    @Test
    void shouldNotCreatePetWithFutureBirthDate() {
        // Given
        PetId id = PetId.generate();
        PetName name = new PetName("Max");
        Species species = new Species("Dog");
        LocalDate futureBirthDate = LocalDate.now().plusDays(1);
        OwnerId ownerId = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(id, name, species, futureBirthDate, ownerId));
    }
    
    @Test
    void shouldNotCreatePetWithNullId() {
        // Given
        PetName name = new PetName("Max");
        Species species = new Species("Dog");
        LocalDate birthDate = LocalDate.now().minusYears(2);
        OwnerId ownerId = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(null, name, species, birthDate, ownerId));
    }
    
    @Test
    void shouldNotCreatePetWithNullName() {
        // Given
        PetId id = PetId.generate();
        Species species = new Species("Dog");
        LocalDate birthDate = LocalDate.now().minusYears(2);
        OwnerId ownerId = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(id, null, species, birthDate, ownerId));
    }
    
    @Test
    void shouldNotCreatePetWithNullSpecies() {
        // Given
        PetId id = PetId.generate();
        PetName name = new PetName("Max");
        LocalDate birthDate = LocalDate.now().minusYears(2);
        OwnerId ownerId = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(id, name, null, birthDate, ownerId));
    }
    
    @Test
    void shouldNotCreatePetWithNullBirthDate() {
        // Given
        PetId id = PetId.generate();
        PetName name = new PetName("Max");
        Species species = new Species("Dog");
        OwnerId ownerId = OwnerId.generate();
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(id, name, species, null, ownerId));
    }
    
    @Test
    void shouldNotCreatePetWithNullOwnerId() {
        // Given
        PetId id = PetId.generate();
        PetName name = new PetName("Max");
        Species species = new Species("Dog");
        LocalDate birthDate = LocalDate.now().minusYears(2);
        
        // When/Then
        assertThrows(IllegalArgumentException.class, 
            () -> Pet.create(id, name, species, birthDate, null));
    }
}