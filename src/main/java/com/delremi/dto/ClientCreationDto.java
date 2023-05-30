package com.delremi.dto;

import com.delremi.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class ClientCreationDto {

    @NotBlank(message = "First name must not be empty")
    @Pattern(regexp = "^$|^[A-Za-züõöäÜÕÖÄ]+$", message = "First name must contain only characters")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Pattern(regexp = "^$|^[A-Za-züõöäÜÕÖÄ]+$", message = "Last name must contain only characters")
    private String lastName;

    @NotBlank(message = "Username must not be empty")
    private String username;

    @Email(message = "Invalid email address format")
    private String email;

    @NotBlank(message = "Address must not be empty")
    private String address;

    @NotNull(message = "Country must be selected")
    private Country country;
}
