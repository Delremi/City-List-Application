package com.delremi.controller;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.pagination.GetPaginationButtons;
import com.delremi.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.delremi.security.Roles.EDITOR;
import static com.delremi.security.Roles.VIEWER;

@Controller
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final GetPaginationButtons getPaginationButtons;

    @GetMapping("/")
    @Secured(VIEWER)
    public String showCityList(
            Model model,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "") String search) {

        var cityPage = cityService.getCities(page, size, search);
        model.addAttribute("cityPage", cityPage);
        model.addAttribute("searchTerm", search);
        model.addAttribute("pageNumbers", getPaginationButtons.execute(cityPage));
        return "cities";
    }

    @GetMapping("/edit/{id}")
    @Secured(EDITOR)
    public String showEditForm(
            @PathVariable int id,
            Model model) throws EntityNotFoundException {

        var city = cityService.getCity(id);
        return loadEditForm(id, model, CityEditDto.of(city.getName(), city.getImageLink()));
    }

    @PostMapping("/edit/{id}")
    @Secured(EDITOR)
    public String updateCity(
            @PathVariable int id,
            @Valid CityEditDto cityEditDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            return loadEditForm(id, model, cityEditDto);
        }
        cityService.updateCity(id, cityEditDto);
        redirectAttributes.addFlashAttribute("updateSuccessCity", cityEditDto.getName());
        return "redirect:/";
    }

    private String loadEditForm(int id, Model model, CityEditDto cityEditDto) {
        model.addAttribute("cityEditDto", cityEditDto);
        model.addAttribute("cityId", id);
        return "edit-city";
    }
}
