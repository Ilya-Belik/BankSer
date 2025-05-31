package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-31T22:29:30+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public CardDto toDto(CardEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CardDto cardDto = new CardDto();

        return cardDto;
    }

    @Override
    public CardEntity toEntity(CardDto dto) {
        if ( dto == null ) {
            return null;
        }

        CardEntity cardEntity = new CardEntity();

        return cardEntity;
    }
}
