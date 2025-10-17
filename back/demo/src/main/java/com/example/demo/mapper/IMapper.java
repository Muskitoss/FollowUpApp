package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IMapper<Object1, Object2> {
    Object1 toDTO(Object2 objet2);
    Object2 toEntity(Object1 objet1);
    List<Object1> toDTO(List<Object2> objet2s);
    List<Object2> toEntity(List<Object1> object1s);
}
