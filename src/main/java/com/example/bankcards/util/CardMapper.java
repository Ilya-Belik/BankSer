package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    //@Mapping(source = "user.username", target = "username")
    CardDto toCardDto(CardEntity cardEntity);

    List<CardDto> toCardDtoList(List<CardEntity> cardEntities);
}
