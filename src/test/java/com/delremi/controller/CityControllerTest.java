package com.delremi.controller;

import com.delremi.dto.CityEditDto;
import com.delremi.model.City;
import com.delremi.model.Role;
import com.delremi.pagination.GetPaginationButtons;
import com.delremi.pagination.PaginationDisabledButton;
import com.delremi.repository.UserRepository;
import com.delremi.security.CustomUserDetails;
import com.delremi.service.CityService;
import com.delremi.service.UserService;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.delremi.security.Roles.EDITOR;
import static com.delremi.security.Roles.VIEWER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
class CityControllerTest {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String DEFAULT_SEARCH_TERM = "";
    private static final int CITY_ID = 11111;
    private static final String CITY_NAME = "city-name";
    private static final String CITY_IMAGE_LINK = "city-image-link";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CityService cityService;
    @MockBean
    private GetPaginationButtons getPaginationButtons;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<CityEditDto> cityEditDtoArgumentCaptor;


    @Test
    void showCityList_whenAuthorized_shouldDisplayMainPage() throws Exception {
        // given
        var page = buildPage(List.of(buildCity()), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        var paginationButton = new PaginationDisabledButton();
        when(cityService.getCities(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SEARCH_TERM)).thenReturn(page);
        when(getPaginationButtons.execute(page)).thenReturn(List.of(paginationButton));

        // when, then
        mockMvc.perform(get("/").with(user(buildUserDetails(VIEWER))))
                .andExpect(status().isOk())
                .andExpect(view().name("cities"))
                .andExpect(model().attribute("cityPage", equalToObject(page)))
                .andExpect(model().attribute("searchTerm", DEFAULT_SEARCH_TERM))
                .andExpect(model().attribute("pageNumbers", Matchers.contains(paginationButton)));
    }

    @Test
    void showCityList_whenAuthorizedWithWrongRole_shouldReturnForbidden() throws Exception {
        // when, then
        mockMvc.perform(get("/").with(user(buildUserDetails(EDITOR))))
                .andExpect(status().isForbidden());
    }

    @Test
    void showCityList_whenNotAuthorized_shouldRedirectToLogin() throws Exception {
        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void showCityList_whenCalledWithRequestParams_shouldDisplayMainPage() throws Exception {
        // given
        var pageNumber = 11;
        var pageSize = 22;
        var searchTerm = "33";
        var page = buildPage(List.of(buildCity()), PageRequest.of(pageNumber, pageSize));
        var paginationButton = new PaginationDisabledButton();
        when(cityService.getCities(pageNumber, pageSize, searchTerm)).thenReturn(page);
        when(getPaginationButtons.execute(page)).thenReturn(List.of(paginationButton));

        // when, then
        mockMvc.perform(get("/")
                        .param("search", searchTerm)
                        .param("page", "" + pageNumber)
                        .param("size", "" + pageSize)
                        .with(user(buildUserDetails(VIEWER))))
                .andExpect(status().isOk())
                .andExpect(view().name("cities"))
                .andExpect(model().attribute("cityPage", equalToObject(page)))
                .andExpect(model().attribute("searchTerm", searchTerm))
                .andExpect(model().attribute("pageNumbers", Matchers.contains(paginationButton)));
    }

    @Test
    void showEditForm_whenAuthorized_shouldDisplayEditForm() throws Exception {
        // given
        var city = buildCity();
        when(cityService.getCity(CITY_ID)).thenReturn(city);

        // when, then
        mockMvc.perform(get("/edit/{cityId}", CITY_ID).with(user(buildUserDetails(EDITOR))))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-city"))
                .andExpect(model().attribute("cityEditDto", equalToObject(CityEditDto.of(CITY_NAME, CITY_IMAGE_LINK))))
                .andExpect(model().attribute("cityId", CITY_ID));

    }

    @Test
    void showEditForm_whenAuthorizedWithWrongRole_shouldReturnForbidden() throws Exception {
        // when, then
        mockMvc.perform(get("/edit/{cityId}", CITY_ID).with(user(buildUserDetails(VIEWER))))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateCity_whenAuthorizedAndValidForm_shouldUpdateCityAndRedirect() throws Exception {
        // given
        var updatedCityName = "Updated City Name";
        var updatedCityImageLink = "updated-city-image-link";

        // when, then
        mockMvc.perform(post("/edit/{cityId}", CITY_ID)
                        .with(user(buildUserDetails(EDITOR)))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildFormData(updatedCityName, updatedCityImageLink)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attribute("updateSuccessCity", updatedCityName));

        verify(cityService).updateCity(eq(CITY_ID), cityEditDtoArgumentCaptor.capture());
        assertThat(cityEditDtoArgumentCaptor.getValue()).isNotNull();
        assertThat(cityEditDtoArgumentCaptor.getValue().getName()).isEqualTo(updatedCityName);
        assertThat(cityEditDtoArgumentCaptor.getValue().getImageLink()).isEqualTo(updatedCityImageLink);
    }

    @Test
    void updateCity_whenAuthorizedAndInvalidForm_shouldNotUpdateCityAndStayOnForm() throws Exception {
        // given
        var invalidUpdatedCityName = "City Name With Numbers 12345";
        var updatedCityImageLink = "updated-city-image-link";

        // when, then
        mockMvc.perform(post("/edit/{cityId}", CITY_ID)
                        .with(user(buildUserDetails(EDITOR)))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(buildFormData(invalidUpdatedCityName, updatedCityImageLink)))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-city"))
                .andExpect(model().attribute("cityEditDto", equalToObject(CityEditDto.of(invalidUpdatedCityName, updatedCityImageLink))))
                .andExpect(model().attribute("cityId", CITY_ID));

        verify(cityService, never()).updateCity(eq(CITY_ID), any());
    }


    @Test
    void updateCity_whenNotAuthorized_shouldNotUpdateCity() throws Exception {
        // when, then
        mockMvc.perform(post("/edit/{cityId}", CITY_ID)
                        .with(user(buildUserDetails(VIEWER))))
                .andExpect(status().isForbidden());

        verify(cityService, never()).updateCity(eq(CITY_ID), any());
    }


    private String buildFormData(String cityName, String cityImageLink) throws IOException, ParseException {
        return EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                new BasicNameValuePair("name", cityName),
                new BasicNameValuePair("imageLink", cityImageLink)
        )));
    }

    private City buildCity() {
        return City.builder()
                .id(CITY_ID)
                .name(CITY_NAME)
                .imageLink(CITY_IMAGE_LINK)
                .build();
    }

    private Page<City> buildPage(List<City> cities, Pageable pageable) {
        return new PageImpl<>(cities, pageable, cities.size());
    }

    private CustomUserDetails buildUserDetails(String... roles) {
        var userRoles = Arrays.stream(roles).map(roleCode -> Role.builder().role(roleCode).build()).collect(Collectors.toList());
        return new CustomUserDetails(null, null, userRoles);
    }
}
