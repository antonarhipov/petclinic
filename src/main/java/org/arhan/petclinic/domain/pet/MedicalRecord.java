package org.arhan.petclinic.domain.pet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MedicalRecord is an entity representing a medical record entry for a pet.
 * It contains information about a veterinary visit, including the date,
 * description, and the veterinarian who performed the examination.
 */
public class MedicalRecord {
    private final MedicalRecordId id;
    private final LocalDateTime date;
    private final String description;
    private final VeterinarianId veterinarianId;
    private final List<Treatment> treatments;

    private MedicalRecord(MedicalRecordId id, LocalDateTime date, String description, VeterinarianId veterinarianId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.veterinarianId = veterinarianId;
        this.treatments = new ArrayList<>();
    }

    /**
     * Creates a new MedicalRecord instance.
     *
     * @param id the unique identifier of the medical record
     * @param date the date and time of the medical record
     * @param description the description of the medical examination
     * @param veterinarianId the ID of the veterinarian who performed the examination
     * @return a new MedicalRecord instance
     */
    public static MedicalRecord create(MedicalRecordId id, LocalDateTime date, String description, VeterinarianId veterinarianId) {
        validateCreation(id, date, description, veterinarianId);
        return new MedicalRecord(id, date, description, veterinarianId);
    }

    private static void validateCreation(MedicalRecordId id, LocalDateTime date, String description, VeterinarianId veterinarianId) {
        if (id == null) {
            throw new IllegalArgumentException("Medical record ID cannot be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (veterinarianId == null) {
            throw new IllegalArgumentException("Veterinarian ID cannot be null");
        }
        if (date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Medical record date cannot be in the future");
        }
    }

    public MedicalRecordId getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public VeterinarianId getVeterinarianId() {
        return veterinarianId;
    }

    public List<Treatment> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }
}