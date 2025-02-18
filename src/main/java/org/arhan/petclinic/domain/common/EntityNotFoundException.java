package org.arhan.petclinic.domain.common;

/**
 * Exception thrown when an entity cannot be found in the repository.
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for when an entity of the given type with the given ID is not found.
     *
     * @param entityType the type of entity that was not found
     * @param id the ID that was searched for
     * @return a new EntityNotFoundException with a descriptive message
     */
    public static EntityNotFoundException withId(String entityType, String id) {
        return new EntityNotFoundException(
            String.format("%s with ID %s not found", entityType, id)
        );
    }

    /**
     * Creates an exception for when an entity of the given type cannot be found using the given criteria.
     *
     * @param entityType the type of entity that was not found
     * @param criteria the search criteria that was used
     * @param value the value that was searched for
     * @return a new EntityNotFoundException with a descriptive message
     */
    public static EntityNotFoundException withCriteria(String entityType, String criteria, String value) {
        return new EntityNotFoundException(
            String.format("%s with %s '%s' not found", entityType, criteria, value)
        );
    }
}