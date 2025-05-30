package com.example.bankcards.controller;

import com.example.bankcards.dto.CardCreateRequest;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardService cardService;
    private final CardMapper mapToCardFilter;


    @GetMapping("/cards")
    public Page<CardDto> getCardsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return cardService.getCardsPage(page, size);
    }

    @PostMapping("/createCard")
    public CardDto createCard(@RequestBody CardCreateRequest request) {
        return cardService.createCard(request);
    }
}