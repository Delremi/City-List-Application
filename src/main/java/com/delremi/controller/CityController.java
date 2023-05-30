package com.delremi.controller;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.City;
import com.delremi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public String showCountryList(Model model) throws EntityNotFoundException {
        model.addAttribute("cities", cityService.getCities());
        return "cities";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, CityEditDto cityEditDto) throws EntityNotFoundException {
        City city = cityService.getCity(id);
        cityEditDto = CityEditDto.of(city.getName(), city.getImageLink());
        return loadEditForm(id, model, cityEditDto);
//        model.addAttribute("cityEditDto", cityEditDto);
//        model.addAttribute("cityId", id);
//        return "edit-city";
    }

    @PostMapping("/edit/{id}")
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
