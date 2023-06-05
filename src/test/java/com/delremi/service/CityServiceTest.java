package com.delremi.service;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.mapper.CityMapper;
import com.delremi.model.City;
import com.delremi.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    private static final String CITY_NAME = "City Name Abc";
    private static final String EDITED_CITY_NAME = "New City Name Abc";
    private static final String CITY_IMAGE_LINK = "city-image-link.foobar";
    private static final String EDITED_CITY_IMAGE_LINK = "new-city-image-link.foobar";
    private static final int CITY_ID = -99999;

    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;
    @InjectMocks
    private CityService cityService;

    @Captor
    private ArgumentCaptor<City> cityArgumentCaptor;

    @Test
    void saveCity_shouldSaveCity() {
        // given
        var cityEditDto = buildCityEditDto();
        var city = buildNewCity();
        when(cityMapper.toEntity(cityEditDto)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);

        // when
        cityService.saveCity(cityEditDto);

        // then
        verify(cityRepository).save(city);
    }

    @Test
    void getCities_shouldReturnCities() {
        // given
        var searchTerm = "search term 123";
        var pageNumber = 404;
        var pageSize = 404;
        var page = buildPage();
        when(cityRepository.findAllByNameContainsIgnoreCase(searchTerm, PageRequest.of(pageNumber, pageSize))).thenReturn(page);

        // when
        var result = cityService.getCities(pageNumber, pageSize, searchTerm);

        // then
        assertThat(result).isEqualTo(page);
    }

    @Test
    void getCity_shouldReturnCity() throws EntityNotFoundException {
        // given
        var city = buildCity();
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.of(city));

        // when
        var result = cityService.getCity(CITY_ID);

        // then
        assertThat(result).isEqualTo(city);
    }

    @Test
    void getCity_whenCityNotFound_shouldThrow() {
        // given
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> cityService.getCity(CITY_ID));
    }

    @Test
    void updateCity_shouldUpdateCity() throws EntityNotFoundException {
        // given
        var city = buildCity();
        var cityEditDto = buildCityEditDto();
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.of(city));

        // when
        cityService.updateCity(CITY_ID, cityEditDto);

        // then
        verify(cityRepository).save(cityArgumentCaptor.capture());
        assertThat(cityArgumentCaptor.getValue().getId()).isEqualTo(CITY_ID);
        assertThat(cityArgumentCaptor.getValue().getName()).isEqualTo(EDITED_CITY_NAME);
        assertThat(cityArgumentCaptor.getValue().getImageLink()).isEqualTo(EDITED_CITY_IMAGE_LINK);
    }

    @Test
    void updateCity_whenCityNotFound_shouldThrow() {
        // given
        var cityEditDto = buildCityEditDto();
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> cityService.updateCity(CITY_ID, cityEditDto));
    }

    private CityEditDto buildCityEditDto() {
        return CityEditDto.of(EDITED_CITY_NAME, EDITED_CITY_IMAGE_LINK);
    }

    private City buildCity() {
        return City.builder()
                .name(CITY_NAME)
                .imageLink(CITY_IMAGE_LINK)
                .id(CITY_ID)
                .build();
    }

    private City buildNewCity() {
        return City.builder()
                .name(EDITED_CITY_NAME)
                .imageLink(EDITED_CITY_IMAGE_LINK)
                .build();
    }

    private Page<City> buildPage() {
        return new PageImpl<>(emptyList(), PageRequest.of(1, 1), 1);
    }
}
