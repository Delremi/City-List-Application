package com.delremi.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class CityEditDto {

    @NotBlank(message = "City name must not be empty")
    @Size(max = 50, message = "City name must not be longer than 50 characters")
    @Pattern(regexp = "^$|^[A-Za-züõöäÜÕÖÄ ]+$", message = "City name must contain only characters")
    private String name;

    @NotBlank(message = "Image link must not be empty")
    @Size(max = 500, message = "Image link must not be longer than 500 characters")
    private String imageLink;
}
