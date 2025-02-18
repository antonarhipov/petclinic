package org.arhan.petclinic.domain.pet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PetNameTest {
    
    @Test
    void shouldCreateValidPetName() {
        // Given
        String name = "Max";
        
        // When
        PetName petName = new PetName(name);
        
        // Then
        assertEquals(name, petName.value());
    }
    
    @Test
    void shouldNotCreatePetNameWithEmptyValue() {
        // Given
        String emptyName = "";
        
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> new PetName(emptyName));
    }
    
    @Test
    void shouldNotCreatePetNameWithNullValue() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> new PetName(null));
    }
    
    @Test
    void shouldTrimPetName() {
        // Given
        String nameWithSpaces = "  Max  ";
        
        // When
        PetName petName = new PetName(nameWithSpaces);
        
        // Then
        assertEquals("Max", petName.value());
    }
    
    @Test
    void shouldNotCreatePetNameExceedingMaxLength() {
        // Given
        String tooLongName = "A".repeat(51); // assuming max length is 50 characters
        
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> new PetName(tooLongName));
    }
    
    @Test
    void shouldCreatePetNameAtMaxLength() {
        // Given
        String maxLengthName = "A".repeat(50); // maximum allowed length
        
        // When
        PetName petName = new PetName(maxLengthName);
        
        // Then
        assertEquals(maxLengthName, petName.value());
    }
}