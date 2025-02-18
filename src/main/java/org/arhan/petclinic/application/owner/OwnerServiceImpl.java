package org.arhan.petclinic.application.owner;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Implementation of OwnerService.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {
    
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerDTO registerOwner(RegisterOwnerCommand command) {
        command.validate();
        
        var owner = Owner.create(
            OwnerId.generate(),
            new FullName(command.firstName(), command.lastName()),
            new ContactInformation(
                command.email().trim().toLowerCase(),
                command.phone(),
                new Address(
                    command.street(),
                    command.city(),
                    command.state(),
                    command.postalCode()
                )
            )
        );
        
        ownerRepository.save(owner);
        return OwnerDTO.fromDomain(owner);
    }

    @Override
    public OwnerDTO updateOwner(UpdateOwnerCommand command) {
        command.validate();
        
        var owner = Owner.create(
            OwnerId.fromString(command.id()),
            new FullName(command.firstName(), command.lastName()),
            new ContactInformation(
                command.email().trim().toLowerCase(),
                command.phone(),
                new Address(
                    command.street(),
                    command.city(),
                    command.state(),
                    command.postalCode()
                )
            )
        );
        
        // Preserve existing pets
        var existingOwner = ownerRepository.findById(OwnerId.fromString(command.id()));
        existingOwner.getPets().forEach(owner::addPet);
        
        ownerRepository.save(owner);
        return OwnerDTO.fromDomain(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnerDTO findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
        
        var owner = ownerRepository.findById(OwnerId.fromString(id.trim()));
        return OwnerDTO.fromDomain(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OwnerDTO> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        return ownerRepository.findByEmail(email.trim().toLowerCase())
            .map(OwnerDTO::fromDomain);
    }
}