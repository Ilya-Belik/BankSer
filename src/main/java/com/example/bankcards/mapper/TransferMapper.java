package com.example.bankcards.mapper;

import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.entity.TransferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferDto toDto(TransferEntity entity);
    TransferEntity toEntity(TransferDto dto);
}
