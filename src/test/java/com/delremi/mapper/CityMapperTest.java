package com.delremi.mapper;

import com.delremi.dto.CityEditDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CityMapperTest {

    private final static String CITY_NAME = "City Name Test";
    private final static String CITY_IMAGE_LINK = "City Image Link";

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @Test
    void toEntity_shouldMapCorrectly() {
        // given
        var cityEditDto = buildCityEditDto();

        // when
        var city = cityMapper.toEntity(cityEditDto);

        // then
        assertThat(city.getId()).isNull();
        assertThat(city.getName()).isEqualTo(CITY_NAME);
        assertThat(city.getImageLink()).isEqualTo(CITY_IMAGE_LINK);
    }

    private CityEditDto buildCityEditDto() {
        return CityEditDto.of(CITY_NAME, CITY_IMAGE_LINK);
    }
}