package org.arhan.petclinic.domain.owner;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OwnerRepositoryTest {
    
    private OwnerRepository repository;
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

    @BeforeEach
    void setUp() {
        repository = createRepository();
    }

    @Test
    void shouldSaveAndFindOwnerById() {
        // Given
        OwnerId id = OwnerId.generate();
        Owner owner = Owner.create(id, VALID_NAME, VALID_CONTACT);
        
        // When
        repository.save(owner);
        Owner found = repository.findById(id);
        
        // Then
        assertNotNull(found);
        assertEquals(id, found.getId());
        assertEquals(VALID_NAME, found.getName());
        assertEquals(VALID_CONTACT, found.getContactInfo());
    }
    
    @Test
    void shouldThrowExceptionWhenOwnerNotFound() {
        // Given
        OwnerId nonExistentId = OwnerId.generate();
        
        // When/Then
        assertThrows(EntityNotFoundException.class,
            () -> repository.findById(nonExistentId));
    }
    
    @Test
    void shouldThrowExceptionWhenFindByNullId() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.findById(null));
    }
    
    @Test
    void shouldThrowExceptionWhenSavingNullOwner() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.save(null));
    }
    
    @Test
    void shouldFindOwnerByEmail() {
        // Given
        Owner owner = Owner.create(OwnerId.generate(), VALID_NAME, VALID_CONTACT);
        repository.save(owner);
        
        // When
        var found = repository.findByEmail(VALID_CONTACT.email());
        
        // Then
        assertTrue(found.isPresent());
        assertEquals(owner.getId(), found.get().getId());
    }
    
    @Test
    void shouldReturnEmptyOptionalWhenEmailNotFound() {
        // When
        var found = repository.findByEmail("nonexistent@example.com");
        
        // Then
        assertTrue(found.isEmpty());
    }
    
    @Test
    void shouldThrowExceptionWhenFindByNullEmail() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.findByEmail(null));
    }
    
    @Test
    void shouldThrowExceptionWhenFindByEmptyEmail() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.findByEmail(""));
    }
    
    @Test
    void shouldUpdateExistingOwner() {
        // Given
        OwnerId id = OwnerId.generate();
        Owner originalOwner = Owner.create(id, VALID_NAME, VALID_CONTACT);
        repository.save(originalOwner);
        
        FullName updatedName = new FullName("Jonathan", "Doe");
        Owner updatedOwner = Owner.create(id, updatedName, VALID_CONTACT);
        
        // When
        repository.save(updatedOwner);
        Owner found = repository.findById(id);
        
        // Then
        assertEquals(updatedName, found.getName());
    }

    private OwnerRepository createRepository() {
        // This will be implemented with the actual repository implementation
        throw new UnsupportedOperationException("Repository implementation needed");
    }
}