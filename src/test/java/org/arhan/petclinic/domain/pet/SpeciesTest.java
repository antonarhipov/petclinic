package org.arhan.petclinic.domain.pet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpeciesTest {
    
    @Test
    void shouldCreateValidSpecies() {
        // Given
        String speciesName = "Dog";
        
        // When
        Species species = new Species(speciesName);
        
        // Then
        assertEquals(speciesName, species.name());
    }
    
    @Test
    void shouldNotCreateSpeciesWithEmptyName() {
        // Given
        String emptyName = "";
        
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> new Species(emptyName));
    }
    
    @Test
    void shouldNotCreateSpeciesWithNullName() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> new Species(null));
    }
    
    @Test
    void shouldTrimSpeciesName() {
        // Given
        String nameWithSpaces = "  Dog  ";
        
        // When
        Species species = new Species(nameWithSpaces);
        
        // Then
        assertEquals("Dog", species.name());
    }
}