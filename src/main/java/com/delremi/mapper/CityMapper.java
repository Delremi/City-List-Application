package com.delremi.mapper;

import com.delremi.dto.CityEditDto;
import com.delremi.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapperConfig.class)
public interface CityMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name")
  @Mapping(target = "imageLink")
  City toEntity(CityEditDto cityEditDto);
}
