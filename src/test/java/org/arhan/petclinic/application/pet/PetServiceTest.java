package org.arhan.petclinic.application.pet;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.Owner;
import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.owner.OwnerRepository;
import org.arhan.petclinic.domain.pet.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    
    @Mock
    private PetRepository petRepository;
    
    @Mock
    private OwnerRepository ownerRepository;
    
    private PetService petService;
    
    private static final LocalDate VALID_BIRTH_DATE = LocalDate.now().minusYears(2);
    private static final String VALID_OWNER_ID = "123e4567-e89b-12d3-a456-426614174000";

    @BeforeEach
    void setUp() {
        petService = new PetServiceImpl(petRepository, ownerRepository);
    }

    @Test
    void shouldRegisterNewPet() {
        // Given
        var command = new RegisterPetCommand(
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(ownerRepository.findById(any(OwnerId.class))).thenReturn(mock(Owner.class));
        
        // When
        var result = petService.registerPet(command);
        
        // Then
        assertNotNull(result);
        assertEquals(command.name(), result.name());
        assertEquals(command.species(), result.species());
        assertEquals(command.birthDate(), result.birthDate());
        assertEquals(command.ownerId(), result.ownerId());
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldThrowExceptionWhenOwnerNotFound() {
        // Given
        var command = new RegisterPetCommand(
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(ownerRepository.findById(any(OwnerId.class)))
            .thenThrow(new EntityNotFoundException("Owner not found"));
        
        // When/Then
        assertThrows(EntityNotFoundException.class, () -> petService.registerPet(command));
        verify(petRepository, never()).save(any(Pet.class));
    }
    
    @Test
    void shouldUpdateExistingPet() {
        // Given
        var petId = "123e4567-e89b-12d3-a456-426614174001";
        var command = new UpdatePetCommand(
            petId,
            "Maxwell",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(ownerRepository.findById(any(OwnerId.class))).thenReturn(mock(Owner.class));
        
        // When
        var result = petService.updatePet(command);
        
        // Then
        assertNotNull(result);
        assertEquals(petId, result.id());
        assertEquals(command.name(), result.name());
        assertEquals(command.species(), result.species());
        assertEquals(command.birthDate(), result.birthDate());
        assertEquals(command.ownerId(), result.ownerId());
        verify(petRepository).save(any(Pet.class));
    }
    
    @Test
    void shouldFindPetById() {
        // Given
        var petId = "123e4567-e89b-12d3-a456-426614174001";
        var pet = Pet.create(
            PetId.fromString(petId),
            new PetName("Max"),
            new Species("Dog"),
            VALID_BIRTH_DATE,
            OwnerId.fromString(VALID_OWNER_ID)
        );
        
        when(petRepository.findById(any(PetId.class))).thenReturn(pet);
        
        // When
        var result = petService.findById(petId);
        
        // Then
        assertNotNull(result);
        assertEquals(petId, result.id());
        assertEquals(pet.getName().value(), result.name());
        assertEquals(pet.getSpecies().name(), result.species());
    }
    
    @Test
    void shouldFindPetsByOwner() {
        // Given
        var pet1 = Pet.create(
            PetId.generate(),
            new PetName("Max"),
            new Species("Dog"),
            VALID_BIRTH_DATE,
            OwnerId.fromString(VALID_OWNER_ID)
        );
        var pet2 = Pet.create(
            PetId.generate(),
            new PetName("Bella"),
            new Species("Cat"),
            VALID_BIRTH_DATE,
            OwnerId.fromString(VALID_OWNER_ID)
        );
        
        when(ownerRepository.findById(any(OwnerId.class))).thenReturn(mock(Owner.class));
        when(petRepository.findByOwner(any(OwnerId.class))).thenReturn(List.of(pet1, pet2));
        
        // When
        var results = petService.findByOwner(VALID_OWNER_ID);
        
        // Then
        assertEquals(2, results.size());
        verify(ownerRepository).findById(any(OwnerId.class));
    }
    
    @Test
    void shouldThrowExceptionWhenPetNotFound() {
        // Given
        var nonExistentId = "123e4567-e89b-12d3-a456-426614174999";
        when(petRepository.findById(any(PetId.class)))
            .thenThrow(new EntityNotFoundException("Pet not found"));
        
        // When/Then
        assertThrows(EntityNotFoundException.class, () -> petService.findById(nonExistentId));
    }
}