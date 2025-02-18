package org.arhan.petclinic.application.pet;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.owner.OwnerRepository;
import org.arhan.petclinic.domain.pet.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implementation of PetService.
 */
@Service
@Transactional
public class PetServiceImpl implements PetService {
    
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetServiceImpl(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public PetDTO registerPet(RegisterPetCommand command) {
        command.validate();
        
        // Verify owner exists
        var ownerId = OwnerId.fromString(command.ownerId());
        ownerRepository.findById(ownerId); // Will throw if not found
        
        var pet = Pet.create(
            PetId.generate(),
            new PetName(command.name()),
            new Species(command.species()),
            command.birthDate(),
            ownerId
        );
        
        petRepository.save(pet);
        return PetDTO.fromDomain(pet);
    }

    @Override
    public PetDTO updatePet(UpdatePetCommand command) {
        command.validate();
        
        // Verify owner exists
        var ownerId = OwnerId.fromString(command.ownerId());
        ownerRepository.findById(ownerId); // Will throw if not found
        
        var pet = Pet.create(
            PetId.fromString(command.id()),
            new PetName(command.name()),
            new Species(command.species()),
            command.birthDate(),
            ownerId
        );
        
        petRepository.save(pet);
        return PetDTO.fromDomain(pet);
    }

    @Override
    @Transactional(readOnly = true)
    public PetDTO findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet ID cannot be null or empty");
        }
        
        var pet = petRepository.findById(PetId.fromString(id.trim()));
        return PetDTO.fromDomain(pet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> findByOwner(String ownerId) {
        if (ownerId == null || ownerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
        
        // Verify owner exists
        var ownerIdObj = OwnerId.fromString(ownerId.trim());
        ownerRepository.findById(ownerIdObj); // Will throw if not found
        
        return petRepository.findByOwner(ownerIdObj)
            .stream()
            .map(PetDTO::fromDomain)
            .toList();
    }
}