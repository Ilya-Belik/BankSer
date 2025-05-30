package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateRequest;
import com.example.bankcards.dto.CardDto;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface CardService {
    /**
     * Создает новую карту.
     * @param cardData данные карты (номер, тип, баланс, статус и т.д.)
     * @return созданная карта
     */
    CardDto createCard(CardCreateRequest cardData);
    Page<CardDto> getCardsPage(int page, int size);

//
//    // ============================
//    // Методы для администратора
//    // ============================
//
//    /**
//     * Создает новую карту.
//     * @param cardData данные карты (номер, тип, баланс, статус и т.д.)
//     * @return созданная карта
//     */
//    Card createCard(CardCreateRequest cardData);
//
//
//
//    /**
//     * Активирует карту по ID.
//     * @param cardId идентификатор карты
//     */
//    void activateCard(Long cardId);
//
//    /**
//     * Удаляет карту по ID.
//     * @param cardId идентификатор карты
//     */
//    void deleteCard(Long cardId);
//
//    /**
//     * Получает список всех карт (для админа).
//     * @param filter параметры фильтрации (по статусу, типу, пользователю и т.д.)
//     * @param pageNumber номер страницы
//     * @param pageSize количество элементов на странице
//     * @return страница с картами
//     */
//    Page<Card> getAllCards(CardFilter filter, int pageNumber, int pageSize);
//
//    /**
//     * Управление пользователями — добавление/удаление/редактирование (может быть отдельным сервисом)
//     */
//
//    // ============================
//    // Методы для пользователя
//    // ============================
//
//    /**
//     * Получает список своих карт с поиском и пагинацией.
//     * @param userId идентификатор пользователя
//     * @param searchQuery строка поиска (по номеру или типу)
//     * @param pageNumber номер страницы
//     * @param pageSize количество элементов на странице
//     * @return страница с картами пользователя
//     */
//    Page<Card> getUserCards(Long userId, String searchQuery, int pageNumber, int pageSize);
//
//    /**
//     * Запросить блокировку своей карты.
//     * @param userId пользователь запрашивает блокировку своей карты по ID
//     */
//    void requestBlockCard(Long userId, Long cardId);
//
//    /**
//     * Перевод между своими картами.
//     * @param userId пользователь инициирует перевод
//     * @param fromCardId карта-отправитель
//     * @param toCardId карта-получатель
//     * @param amount сумма перевода
//     */
//    void transferBetweenUserCards(Long userId, Long fromCardId, Long toCardId, BigDecimal amount);
//
//    /**
//     * Получить баланс своей карты.
//     * @param userId пользователь запрашивает баланс своей карты по ID
//     */
//    BigDecimal getBalance(Long userId, Long cardId);
}