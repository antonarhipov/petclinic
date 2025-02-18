package org.arhan.petclinic.application.owner;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.*;
import org.arhan.petclinic.domain.pet.PetId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    
    @Mock
    private OwnerRepository ownerRepository;
    
    private OwnerService ownerService;
    
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PHONE = "+12345678901";
    private static final String VALID_STREET = "123 Main St";
    private static final String VALID_CITY = "Springfield";
    private static final String VALID_STATE = "IL";
    private static final String VALID_POSTAL_CODE = "62701";

    @BeforeEach
    void setUp() {
        ownerService = new OwnerServiceImpl(ownerRepository);
    }

    @Test
    void shouldRegisterNewOwner() {
        // Given
        var command = new RegisterOwnerCommand(
            "John",
            "Doe",
            VALID_EMAIL,
            VALID_PHONE,
            VALID_STREET,
            VALID_CITY,
            VALID_STATE,
            VALID_POSTAL_CODE
        );
        
        // When
        var result = ownerService.registerOwner(command);
        
        // Then
        assertNotNull(result);
        assertEquals(command.firstName(), result.firstName());
        assertEquals(command.lastName(), result.lastName());
        assertEquals(command.email(), result.email());
        assertEquals(command.phone(), result.phone());
        assertEquals(command.street(), result.address().street());
        assertTrue(result.petIds().isEmpty());
        verify(ownerRepository).save(any(Owner.class));
    }
    
    @Test
    void shouldUpdateExistingOwner() {
        // Given
        var ownerId = "123e4567-e89b-12d3-a456-426614174000";
        var command = new UpdateOwnerCommand(
            ownerId,
            "Jonathan",
            "Doe",
            VALID_EMAIL,
            VALID_PHONE,
            VALID_STREET,
            VALID_CITY,
            VALID_STATE,
            VALID_POSTAL_CODE
        );
        
        var existingOwner = Owner.create(
            OwnerId.fromString(ownerId),
            new FullName("John", "Doe"),
            new ContactInformation(
                VALID_EMAIL,
                VALID_PHONE,
                new Address(VALID_STREET, VALID_CITY, VALID_STATE, VALID_POSTAL_CODE)
            )
        );
        existingOwner.addPet(PetId.generate());
        
        when(ownerRepository.findById(any(OwnerId.class))).thenReturn(existingOwner);
        
        // When
        var result = ownerService.updateOwner(command);
        
        // Then
        assertNotNull(result);
        assertEquals(command.firstName(), result.firstName());
        assertEquals(command.lastName(), result.lastName());
        assertEquals(1, result.petIds().size());
        verify(ownerRepository).save(any(Owner.class));
    }
    
    @Test
    void shouldFindOwnerById() {
        // Given
        var ownerId = "123e4567-e89b-12d3-a456-426614174000";
        var owner = Owner.create(
            OwnerId.fromString(ownerId),
            new FullName("John", "Doe"),
            new ContactInformation(
                VALID_EMAIL,
                VALID_PHONE,
                new Address(VALID_STREET, VALID_CITY, VALID_STATE, VALID_POSTAL_CODE)
            )
        );
        
        when(ownerRepository.findById(any(OwnerId.class))).thenReturn(owner);
        
        // When
        var result = ownerService.findById(ownerId);
        
        // Then
        assertNotNull(result);
        assertEquals(ownerId, result.id());
        assertEquals(owner.getName().firstName(), result.firstName());
        assertEquals(owner.getName().lastName(), result.lastName());
    }
    
    @Test
    void shouldFindOwnerByEmail() {
        // Given
        var owner = Owner.create(
            OwnerId.generate(),
            new FullName("John", "Doe"),
            new ContactInformation(
                VALID_EMAIL,
                VALID_PHONE,
                new Address(VALID_STREET, VALID_CITY, VALID_STATE, VALID_POSTAL_CODE)
            )
        );
        
        when(ownerRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(owner));
        
        // When
        var result = ownerService.findByEmail(VALID_EMAIL);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals(VALID_EMAIL, result.get().email());
    }
    
    @Test
    void shouldReturnEmptyOptionalWhenEmailNotFound() {
        // Given
        when(ownerRepository.findByEmail(any())).thenReturn(Optional.empty());
        
        // When
        var result = ownerService.findByEmail("nonexistent@example.com");
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    @Test
    void shouldThrowExceptionWhenOwnerNotFound() {
        // Given
        var nonExistentId = "123e4567-e89b-12d3-a456-426614174999";
        when(ownerRepository.findById(any(OwnerId.class)))
            .thenThrow(new EntityNotFoundException("Owner not found"));
        
        // When/Then
        assertThrows(EntityNotFoundException.class, () -> ownerService.findById(nonExistentId));
    }
    
    @Test
    void shouldNormalizeEmailOnRegistration() {
        // Given
        var command = new RegisterOwnerCommand(
            "John",
            "Doe",
            "  John.Doe@Example.com  ",
            VALID_PHONE,
            VALID_STREET,
            VALID_CITY,
            VALID_STATE,
            VALID_POSTAL_CODE
        );
        
        // When
        var result = ownerService.registerOwner(command);
        
        // Then
        assertEquals("john.doe@example.com", result.email());
    }
}