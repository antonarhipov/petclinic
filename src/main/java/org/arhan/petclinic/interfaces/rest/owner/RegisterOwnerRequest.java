package org.arhan.petclinic.interfaces.rest.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.arhan.petclinic.application.owner.RegisterOwnerCommand;

/**
 * Request DTO for registering a new owner.
 */
public record RegisterOwnerRequest(
    @NotBlank(message = "First name is required")
    String firstName,

    @NotBlank(message = "Last name is required")
    String lastName,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Phone is required")
    @Pattern(
        regexp = "^\\+?[1-9]\\d{1,14}$",
        message = "Invalid phone format. Use E.164 format (e.g., +1234567890)"
    )
    String phone,

    @NotBlank(message = "Street is required")
    String street,

    @NotBlank(message = "City is required")
    String city,

    @NotBlank(message = "State is required")
    String state,

    @NotBlank(message = "Postal code is required")
    String postalCode
) {
    /**
     * Converts this request to a command object.
     *
     * @return the command object
     */
    public RegisterOwnerCommand toCommand() {
        return new RegisterOwnerCommand(
            firstName,
            lastName,
            email,
            phone,
            street,
            city,
            state,
            postalCode
        );
    }
}