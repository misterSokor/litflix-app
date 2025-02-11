package com.store.litflix.dto.user;

import com.store.litflix.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords do not match.")
public class UserRegistrationRequestDto {

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password is mandatory.")
    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
    private String password;

    @NotBlank(message = "Repeat Password is mandatory.")
    @Length(min = 8, max = 20, message = "Repeat Password must be between 8 and 20 characters.")
    private String repeatPassword;

    @NotBlank(message = "First name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last name is mandatory.")
    private String lastName;

    private String shippingAddress;
}
