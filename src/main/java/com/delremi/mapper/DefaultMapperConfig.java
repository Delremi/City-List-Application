package com.delremi.mapper;

import org.mapstruct.MapperConfig;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@MapperConfig(componentModel = "spring", unmappedSourcePolicy = IGNORE, unmappedTargetPolicy = ERROR)
public abstract class DefaultMapperConfig { }
