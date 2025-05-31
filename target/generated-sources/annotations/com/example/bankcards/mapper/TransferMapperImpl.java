package com.example.bankcards.mapper;

import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.entity.TransferEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-31T22:29:30+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class TransferMapperImpl implements TransferMapper {

    @Override
    public TransferDto toDto(TransferEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TransferDto transferDto = new TransferDto();

        return transferDto;
    }

    @Override
    public TransferEntity toEntity(TransferDto dto) {
        if ( dto == null ) {
            return null;
        }

        TransferEntity transferEntity = new TransferEntity();

        return transferEntity;
    }
}
