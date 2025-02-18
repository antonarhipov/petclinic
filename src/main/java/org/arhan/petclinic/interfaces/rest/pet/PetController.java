package org.arhan.petclinic.interfaces.rest.pet;

import jakarta.validation.Valid;
import org.arhan.petclinic.application.pet.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing pets.
 */
@RestController
@RequestMapping("/pets")
public class PetController {
    
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * Registers a new pet.
     *
     * @param request the registration request
     * @return the registered pet
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse registerPet(@Valid @RequestBody RegisterPetRequest request) {
        var pet = petService.registerPet(request.toCommand());
        return PetResponse.fromDTO(pet);
    }

    /**
     * Updates an existing pet.
     *
     * @param id the ID of the pet to update
     * @param request the update request
     * @return the updated pet
     */
    @PutMapping("/{id}")
    public PetResponse updatePet(
        @PathVariable String id,
        @Valid @RequestBody UpdatePetRequest request
    ) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path must match ID in request body");
        }
        var pet = petService.updatePet(request.toCommand());
        return PetResponse.fromDTO(pet);
    }

    /**
     * Finds a pet by its ID.
     *
     * @param id the ID of the pet to find
     * @return the pet
     */
    @GetMapping("/{id}")
    public PetResponse findById(@PathVariable String id) {
        var pet = petService.findById(id);
        return PetResponse.fromDTO(pet);
    }

    /**
     * Finds all pets owned by the given owner.
     *
     * @param ownerId the ID of the owner
     * @return list of pets owned by the owner
     */
    @GetMapping
    public List<PetResponse> findByOwner(@RequestParam String ownerId) {
        return petService.findByOwner(ownerId)
            .stream()
            .map(PetResponse::fromDTO)
            .toList();
    }
}