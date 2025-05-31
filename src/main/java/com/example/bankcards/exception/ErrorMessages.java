package com.example.bankcards.exception;

public interface ErrorMessages {
    String CARD_NOT_FOUND = "Карта не найдена: %s";
    String INVALID_STATUS = "Недопустимый статус: %s";
    String STATUS_ALREADY_SET = "Карта уже имеет статус: %s";
    String NOT_ENOUGH_MONEY = "На счету недостаточно средств";
}