package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IMapper<DataTransferObject, Entity> {
    DataTransferObject toDTO(Entity entity);
    Entity toEntity(DataTransferObject dataTransferObject);
    List<DataTransferObject> toDTO(List<Entity> entities);
    List<Entity> toEntity(List<DataTransferObject> dataTransferObjects);
}
