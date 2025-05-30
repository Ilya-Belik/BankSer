package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    //@Mapping(source = "user.username", target = "username")
    CardDto toCardDto(Card card);

    List<CardDto> toCardDtoList(List<Card> cards);
}
