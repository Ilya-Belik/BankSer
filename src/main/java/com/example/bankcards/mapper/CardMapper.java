package com.example.bankcards.mapper;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CardMapper { CardDto toDto(CardEntity entity);
    CardEntity toEntity(CardDto dto);
}
