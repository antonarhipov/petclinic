package org.arhan.petclinic.infrastructure.persistence.owner;

import jakarta.persistence.*;
import org.arhan.petclinic.domain.owner.*;
import org.arhan.petclinic.domain.pet.PetId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA entity for persisting Owner aggregate root.
 */
@Entity
@Table(name = "owners")
public class OwnerJpaEntity {
    
    @Id
    private String id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    
    @ElementCollection
    @CollectionTable(name = "owner_pets", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "pet_id")
    private List<String> petIds = new ArrayList<>();

    protected OwnerJpaEntity() {
        // Required by JPA
    }

    public static OwnerJpaEntity fromDomain(Owner owner) {
        var entity = new OwnerJpaEntity();
        entity.id = owner.getId().value();
        entity.firstName = owner.getName().firstName();
        entity.lastName = owner.getName().lastName();
        entity.email = owner.getContactInfo().email();
        entity.phone = owner.getContactInfo().phone();
        entity.street = owner.getContactInfo().address().street();
        entity.city = owner.getContactInfo().address().city();
        entity.state = owner.getContactInfo().address().state();
        entity.postalCode = owner.getContactInfo().address().postalCode();
        entity.petIds = owner.getPets().stream()
            .map(PetId::value)
            .collect(Collectors.toList());
        return entity;
    }

    public Owner toDomain() {
        var fullName = new FullName(firstName, lastName);
        var address = new Address(street, city, state, postalCode);
        var contactInfo = new ContactInformation(email, phone, address);
        var owner = Owner.create(OwnerId.fromString(id), fullName, contactInfo);
        petIds.stream()
            .map(PetId::fromString)
            .forEach(owner::addPet);
        return owner;
    }

    // Getters and setters required by JPA
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<String> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<String> petIds) {
        this.petIds = petIds;
    }
}