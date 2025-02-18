package org.arhan.petclinic.interfaces.rest.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.arhan.petclinic.application.pet.PetDTO;
import org.arhan.petclinic.application.pet.PetService;
import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private PetService petService;
    
    private static final LocalDate VALID_BIRTH_DATE = LocalDate.now().minusYears(2);
    private static final String VALID_OWNER_ID = "123e4567-e89b-12d3-a456-426614174000";

    @Test
    void shouldRegisterNewPet() throws Exception {
        // Given
        var request = new RegisterPetRequest(
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        var response = new PetDTO(
            "123e4567-e89b-12d3-a456-426614174001",
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(petService.registerPet(any())).thenReturn(response);
        
        // When/Then
        mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.name").value(response.name()))
            .andExpect(jsonPath("$.species").value(response.species()));
    }
    
    @Test
    void shouldRejectInvalidRequest() throws Exception {
        // Given
        var request = new RegisterPetRequest(
            "",  // invalid name
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        // When/Then
        mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void shouldUpdateExistingPet() throws Exception {
        // Given
        var petId = "123e4567-e89b-12d3-a456-426614174001";
        var request = new UpdatePetRequest(
            petId,
            "Maxwell",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        var response = new PetDTO(
            petId,
            "Maxwell",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(petService.updatePet(any())).thenReturn(response);
        
        // When/Then
        mockMvc.perform(put("/pets/{id}", petId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.name").value(response.name()));
    }
    
    @Test
    void shouldRejectUpdateWithMismatchedId() throws Exception {
        // Given
        var pathId = "123e4567-e89b-12d3-a456-426614174001";
        var request = new UpdatePetRequest(
            "different-id",
            "Maxwell",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        // When/Then
        mockMvc.perform(put("/pets/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void shouldFindPetById() throws Exception {
        // Given
        var petId = "123e4567-e89b-12d3-a456-426614174001";
        var response = new PetDTO(
            petId,
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(petService.findById(petId)).thenReturn(response);
        
        // When/Then
        mockMvc.perform(get("/pets/{id}", petId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(response.id()))
            .andExpect(jsonPath("$.name").value(response.name()));
    }
    
    @Test
    void shouldReturn404WhenPetNotFound() throws Exception {
        // Given
        var nonExistentId = "123e4567-e89b-12d3-a456-426614174999";
        when(petService.findById(nonExistentId))
            .thenThrow(new EntityNotFoundException("Pet not found"));
        
        // When/Then
        mockMvc.perform(get("/pets/{id}", nonExistentId))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldFindPetsByOwner() throws Exception {
        // Given
        var pet1 = new PetDTO(
            "id1",
            "Max",
            "Dog",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        var pet2 = new PetDTO(
            "id2",
            "Bella",
            "Cat",
            VALID_BIRTH_DATE,
            VALID_OWNER_ID
        );
        
        when(petService.findByOwner(VALID_OWNER_ID)).thenReturn(List.of(pet1, pet2));
        
        // When/Then
        mockMvc.perform(get("/pets")
                .param("ownerId", VALID_OWNER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(pet1.id()))
            .andExpect(jsonPath("$[1].id").value(pet2.id()));
    }
}