package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateRequest;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.CardStatusEnum;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.CardOperationException;
import com.example.bankcards.exception.ErrorMessages;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private static final Random random = new Random();
    private final CardRepository cardRepository;
    private final UserService userService;
    private final CardMapper cardMapper;

    @Override
    public CardDto createCard(CardCreateRequest request) {
        UserEntity currentUserEntity = userService.getCurrentUser();

        boolean isAdmin = currentUserEntity.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ROLE_ADMIN);

        UserEntity cardOwner = isAdmin
                ? userService.findUserById(request.getUserId())
                : currentUserEntity;

        CardEntity cardEntity = new CardEntity();
        cardEntity.setUser(cardOwner);

        LocalDate validityPeriod = LocalDate.now().plusYears(4);
        cardEntity.setValidityPeriod(validityPeriod);
        cardEntity.setBalance(BigDecimal.ZERO);
        cardEntity.setStatus(CardStatusEnum.ACTIVE);
        cardEntity.setCardNumber(generateCardNumber());

        CardEntity savedCardEntity = cardRepository.save(cardEntity);
        return cardMapper.toDto(savedCardEntity);
    }

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(random.nextInt(10));
            }
            if (i < 2) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    @Override
    public Page<CardDto> getCardsPage(int page, int size) {
        Page<CardEntity> pageOfCards = cardRepository.findAll(PageRequest.of(page, size));
        List<CardDto> cardDtos = pageOfCards.getContent().stream()
                .map(cardMapper::toDto)
                .toList();
        return new PageImpl<>(cardDtos, pageOfCards.getPageable(), pageOfCards.getTotalElements());
    }


    private void changeCardStatus(UUID cardId, String status) {
        if (!status.equals("ACTIVE") && !status.equals("BLOCK")) {
            throw new IllegalArgumentException("Invalid card status: " + status);
        }

        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.CARD_NOT_FOUND, cardId)));

        cardEntity.setStatus(CardStatusEnum.BLOCKED);
        cardRepository.save(cardEntity);
    }

    @Override
    @Transactional
    public CardDto transferBetweenUserCards(UUID userId, TransferRequest request) {
        CardEntity fromCard = cardRepository.findById(request.getFromCardId())
                .orElseThrow(() -> new CardOperationException(
                        String.format(ErrorMessages.CARD_NOT_FOUND, request.getFromCardId()), 404));

        CardEntity toCard = cardRepository.findById(request.getToCardId())
                .orElseThrow(() -> new CardOperationException(
                        String.format(ErrorMessages.CARD_NOT_FOUND, request.getToCardId()), 404));

        if (!fromCard.getUser().getId().equals(userId) || !toCard.getUser().getId().equals(userId)) {
            throw new CardOperationException("Доступ запрещён: карты не принадлежат пользователю", 403);
        }

        if (fromCard.getBalance().compareTo(request.getAmount()) < 0) {
            throw new CardOperationException(ErrorMessages.NOT_ENOUGH_MONEY, 400);
        }

        fromCard.setBalance(fromCard.getBalance().subtract(request.getAmount()));
        toCard.setBalance(toCard.getBalance().add(request.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return cardMapper.toDto(fromCard);
    }



//
//    @Override
//    public Page<Card> getUserCards(Long userId, String searchQuery, int pageNumber, int pageSize) {
//        List<Card> userCards = cardRepository.stream()
//                .filter(c -> c.getUserId().equals(userId))
//                .filter(c -> searchQuery == null || c.getNumber().contains(searchQuery) || c.getType().contains(searchQuery))
//                .collect(Collectors.toList());
//
//        int total = userCards.size();
//        int start = Math.min(pageNumber * pageSize, total);
//        int end = Math.min(start + pageSize, total);
//        List<Card> pageContent = userCards.subList(start, end);
//
//        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), total);
//    }
//
//
//    @Override
//    public BigDecimal getBalance(Long userId, Long cardId) {
//        Card card = findById(cardId);
//        if (card == null || !card.getUserId().equals(userId)) {
//            throw new RuntimeException("Карты не существует или доступ запрещен");
//        }
//
//        return card.getBalance();
//    }

}
