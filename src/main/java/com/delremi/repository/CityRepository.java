package com.delremi.repository;

import com.delremi.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {
    Page<City> findAllByNameContainsIgnoreCase(String searchTerm, Pageable pageable);
}
