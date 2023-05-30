package com.delremi.repository;

import com.delremi.model.City;
import com.delremi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {

}
