package org.arhan.petclinic.domain.owner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwnerIdTest {
    
    @Test
    void shouldCreateNewOwnerId() {
        // When
        OwnerId ownerId = OwnerId.generate();
        
        // Then
        assertNotNull(ownerId);
        assertNotNull(ownerId.value());
        assertFalse(ownerId.value().isEmpty());
    }
    
    @Test
    void shouldCreateOwnerIdFromExistingValue() {
        // Given
        String existingId = "123e4567-e89b-12d3-a456-426614174000";
        
        // When
        OwnerId ownerId = OwnerId.fromString(existingId);
        
        // Then
        assertEquals(existingId, ownerId.value());
    }
    
    @Test
    void shouldNotCreateOwnerIdFromInvalidValue() {
        // Given
        String invalidId = "invalid-uuid";
        
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> OwnerId.fromString(invalidId));
    }
    
    @Test
    void shouldNotCreateOwnerIdFromNullValue() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> OwnerId.fromString(null));
    }
    
    @Test
    void shouldNotCreateOwnerIdFromEmptyValue() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> OwnerId.fromString(""));
    }
    
    @Test
    void shouldProduceUniqueIds() {
        // When
        OwnerId id1 = OwnerId.generate();
        OwnerId id2 = OwnerId.generate();
        
        // Then
        assertNotEquals(id1, id2);
        assertNotEquals(id1.value(), id2.value());
    }
}