package com.delremi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class CityEditDto {

    @NotBlank(message = "City name must not be empty")
    @Size(max = 50, message = "City name must not be longer than 50 characters")
    @Pattern(regexp = "^$|^[A-Za-züõöäÜÕÖÄ ]+$", message = "City name must contain only characters")
    private String name;

    @NotBlank(message = "Image link must not be empty")
    @Size(max = 1000, message = "Image link must not be longer than 1000 characters")
    private String imageLink;
}
