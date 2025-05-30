package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateRequest;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.CardStatusEnum;
import com.example.bankcards.entity.RoleEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.ErrorMessages;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.UserService;
import com.example.bankcards.util.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
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
        var currentUserEntity = userService.getCurrentUser();


        boolean isAdmin = false;

        UserEntity cardOwner = isAdmin
                ? userService.findUserById(request.getUserId())
                : currentUserEntity;

        CardEntity cardEntity = new CardEntity();
        cardEntity.setUserEntity(cardOwner);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 4);
        cardEntity.setValidityPeriod(cal.getTime());
        cardEntity.setBalance(BigDecimal.ZERO);
        cardEntity.setStatus(CardStatusEnum.ACTIVE);
        cardEntity.setCardNumber(generateCardNumber());

        CardEntity savedCardEntity = cardRepository.save(cardEntity);
        return cardMapper.toCardDto(savedCardEntity);
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
        List<CardDto> cardDtos = pageOfCards.stream()
                .map(c -> new CardDto(
                        c.getUserEntity().getUsername(),
                        c.getCardNumber(),
                        c.getValidityPeriod(),
                        c.getBalance(),
                        c.getStatus()
                                ))
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
//    @Override
//    public void transferBetweenUserCards(Long userId, Long fromCardId, Long toCardId, BigDecimal amount) {
//        Card fromCard = findById(fromCardId);
//        Card toCard = findById(toCardId);
//
//        if (fromCard == null || toCard == null || !fromCard.getUserId().equals(userId) || !toCard.getUserId().equals(userId)) {
//            throw new RuntimeException("Invalid cards or access denied");
//        }
//
//        if (fromCard.getBalance().compareTo(amount) < 0) {
//            throw new RuntimeException("Insufficient funds");
//        }
//
//        fromCard.setBalance(fromCard.getBalance().subtract(amount));
//        toCard.setBalance(toCard.getBalance().add(amount));
//
//        // Обновление данных в репозитории при необходимости
//    }
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
