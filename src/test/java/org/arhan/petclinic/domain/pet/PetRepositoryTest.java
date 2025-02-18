package org.arhan.petclinic.domain.pet;

import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PetRepositoryTest {
    
    private PetRepository repository;
    private static final PetName VALID_NAME = new PetName("Max");
    private static final Species VALID_SPECIES = new Species("Dog");
    private static final LocalDate VALID_BIRTH_DATE = LocalDate.now().minusYears(2);
    private static final OwnerId VALID_OWNER_ID = OwnerId.generate();

    @BeforeEach
    void setUp() {
        repository = createRepository();
    }

    @Test
    void shouldSaveAndFindPetById() {
        // Given
        PetId id = PetId.generate();
        Pet pet = Pet.create(id, VALID_NAME, VALID_SPECIES, VALID_BIRTH_DATE, VALID_OWNER_ID);
        
        // When
        repository.save(pet);
        Pet found = repository.findById(id);
        
        // Then
        assertNotNull(found);
        assertEquals(id, found.getId());
        assertEquals(VALID_NAME, found.getName());
        assertEquals(VALID_SPECIES, found.getSpecies());
        assertEquals(VALID_BIRTH_DATE, found.getBirthDate());
        assertEquals(VALID_OWNER_ID, found.getOwnerId());
    }
    
    @Test
    void shouldThrowExceptionWhenPetNotFound() {
        // Given
        PetId nonExistentId = PetId.generate();
        
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
    void shouldThrowExceptionWhenSavingNullPet() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.save(null));
    }
    
    @Test
    void shouldFindPetsByOwner() {
        // Given
        OwnerId ownerId = OwnerId.generate();
        Pet pet1 = Pet.create(PetId.generate(), VALID_NAME, VALID_SPECIES, VALID_BIRTH_DATE, ownerId);
        Pet pet2 = Pet.create(PetId.generate(), new PetName("Bella"), VALID_SPECIES, VALID_BIRTH_DATE, ownerId);
        repository.save(pet1);
        repository.save(pet2);
        
        // When
        List<Pet> found = repository.findByOwner(ownerId);
        
        // Then
        assertEquals(2, found.size());
        assertTrue(found.stream().allMatch(pet -> pet.getOwnerId().equals(ownerId)));
    }
    
    @Test
    void shouldReturnEmptyListWhenOwnerHasNoPets() {
        // Given
        OwnerId ownerWithNoPets = OwnerId.generate();
        
        // When
        List<Pet> found = repository.findByOwner(ownerWithNoPets);
        
        // Then
        assertTrue(found.isEmpty());
    }
    
    @Test
    void shouldThrowExceptionWhenFindByNullOwnerId() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
            () -> repository.findByOwner(null));
    }
    
    @Test
    void shouldUpdateExistingPet() {
        // Given
        PetId id = PetId.generate();
        Pet originalPet = Pet.create(id, VALID_NAME, VALID_SPECIES, VALID_BIRTH_DATE, VALID_OWNER_ID);
        repository.save(originalPet);
        
        PetName updatedName = new PetName("Maxwell");
        Pet updatedPet = Pet.create(id, updatedName, VALID_SPECIES, VALID_BIRTH_DATE, VALID_OWNER_ID);
        
        // When
        repository.save(updatedPet);
        Pet found = repository.findById(id);
        
        // Then
        assertEquals(updatedName, found.getName());
    }

    private PetRepository createRepository() {
        // This will be implemented with the actual repository implementation
        throw new UnsupportedOperationException("Repository implementation needed");
    }
}