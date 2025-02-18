package org.arhan.petclinic.domain.pet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PetIdTest {
    
    @Test
    void shouldCreateNewPetId() {
        // When
        PetId petId = PetId.generate();
        
        // Then
        assertNotNull(petId);
        assertNotNull(petId.value());
        assertFalse(petId.value().isEmpty());
    }
    
    @Test
    void shouldCreatePetIdFromExistingValue() {
        // Given
        String existingId = "123e4567-e89b-12d3-a456-426614174000";
        
        // When
        PetId petId = PetId.fromString(existingId);
        
        // Then
        assertEquals(existingId, petId.value());
    }
    
    @Test
    void shouldNotCreatePetIdFromInvalidValue() {
        // Given
        String invalidId = "invalid-uuid";
        
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> PetId.fromString(invalidId));
    }
    
    @Test
    void shouldNotCreatePetIdFromNullValue() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> PetId.fromString(null));
    }
    
    @Test
    void shouldNotCreatePetIdFromEmptyValue() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> PetId.fromString(""));
    }
    
    @Test
    void shouldProduceUniqueIds() {
        // When
        PetId id1 = PetId.generate();
        PetId id2 = PetId.generate();
        
        // Then
        assertNotEquals(id1, id2);
        assertNotEquals(id1.value(), id2.value());
    }
}