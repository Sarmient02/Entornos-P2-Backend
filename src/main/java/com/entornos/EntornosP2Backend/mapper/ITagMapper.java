package com.entornos.EntornosP2Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ITagMapper {

    ITagMapper INSTANCE = Mappers.getMapper(ITagMapper.class);

}
