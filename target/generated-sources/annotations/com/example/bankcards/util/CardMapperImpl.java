package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-30T22:27:29+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public CardDto toCardDto(CardEntity cardEntity) {
        if ( cardEntity == null ) {
            return null;
        }

        CardDto cardDto = new CardDto();

        return cardDto;
    }

    @Override
    public List<CardDto> toCardDtoList(List<CardEntity> cardEntities) {
        if ( cardEntities == null ) {
            return null;
        }

        List<CardDto> list = new ArrayList<CardDto>( cardEntities.size() );
        for ( CardEntity cardEntity : cardEntities ) {
            list.add( toCardDto( cardEntity ) );
        }

        return list;
    }
}
