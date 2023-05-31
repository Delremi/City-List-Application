package com.delremi.controller;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.City;
import com.delremi.pagination.GetPaginationNumbers;
import com.delremi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.delremi.security.Roles.EDITOR;
import static com.delremi.security.Roles.VIEWER;

@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private GetPaginationNumbers getPaginationNumbers;

    @GetMapping
    @Secured(VIEWER)
    public String showCityList(
            Model model,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size,
            @RequestParam(defaultValue = "") String search) throws EntityNotFoundException {

        Page<City> cityPage = cityService.getCities(page, size, search);
        model.addAttribute("cityPage", cityPage);
        model.addAttribute("searchTerm", search);
        model.addAttribute("pageNumbers", getPaginationNumbers.execute(cityPage));
        return "cities";
    }

    @GetMapping("/edit/{id}")
    @Secured(EDITOR)
    public String showEditForm(@PathVariable int id, Model model, CityEditDto cityEditDto) throws EntityNotFoundException {
        City city = cityService.getCity(id);
        cityEditDto = CityEditDto.of(city.getName(), city.getImageLink());
        return loadEditForm(id, model, cityEditDto);
    }

    @PostMapping("/edit/{id}")
    @Secured(EDITOR)
    public String updateCity(@PathVariable int id, @Valid CityEditDto cityEditDto, BindingResult bindingResult, Model model) throws EntityNotFoundException {
        if (bindingResult.hasErrors()) {
            return loadEditForm(id, model, cityEditDto);
        }
        cityService.updateCity(id, cityEditDto);
        return "redirect:/";
    }

    private String loadEditForm(int id, Model model, CityEditDto cityEditDto) {
        model.addAttribute("cityEditDto", cityEditDto);
        model.addAttribute("cityId", id);
        return "edit-city";
    }
}
