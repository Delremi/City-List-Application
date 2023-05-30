package com.delremi.service;

import com.delremi.dto.CityEditDto;
import com.delremi.exception.EntityNotFoundException;
import com.delremi.model.City;
import com.delremi.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveCity(CityEditDto cityEditDto) throws EntityNotFoundException {
        City city = new City();
        city.setName(cityEditDto.getName());
        city.setImageLink(cityEditDto.getImageLink());
        City result = cityRepository.save(city);
        log.info("Saved City with ID " + result.getId());
    }

    @Transactional(readOnly = true)
    public Page<City> getCities(int page, int pageSize, String searchTerm) throws EntityNotFoundException {
        return cityRepository.findAllByNameContainsIgnoreCase(searchTerm, PageRequest.of(page, pageSize));
    }

    @Transactional(readOnly = true)
    public City getCity(int id) throws EntityNotFoundException {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(City.class, id));
    }

    @Transactional
    public void updateCity(int id, CityEditDto cityEditDto) throws EntityNotFoundException {
        City city = getCity(id);
        city.setName(cityEditDto.getName());
        city.setImageLink(cityEditDto.getImageLink());
        cityRepository.save(city);
        log.info("Updated City with ID " + id);
    }
}
