package com.example.skyengtest.controller;

import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.service.PostalItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class PostalItemController {

    private final PostalItemService postalItemService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    PostalItemResponseDto registrationPostalItem(
            @RequestBody @Valid PostalItemRequestDto postalItemDto) {
        log.info("Запрос на регистрацию посылки");
        return postalItemService.registrationPostalItem(postalItemDto);
    }

    @PutMapping("/sending")
    PostalItemResponseDto sendingParcel(@RequestParam(name = "itemId") Long itemId,
                                        @RequestParam(name = "postOfficeId") Long postOfficeId) {
        log.info("Запрос на отправку из почтового отделения");
        return postalItemService.sendingParcel(itemId, postOfficeId);
    }

    @PutMapping("/accept")
    PostalItemResponseDto acceptParcel(@RequestParam(name = "itemId", required = true) Long itemId,
                                       @RequestParam(name = "postOfficeId", required = true) Long postOfficeId) {
        log.info("Запрос на прибытие в промежуточное почтовое отделение");
        return postalItemService.acceptParcel(itemId, postOfficeId);
    }

    @PutMapping("/delivery")
    PostalItemResponseDto deliveredParcel(@RequestParam(name = "itemId", required = true) Long itemId,
                                          @RequestParam(name = "postOfficeId", required = true) Long postOfficeId) {
        log.info("Запрос на прибытие в конечный пункт");
        return postalItemService.deliveredParcel(itemId, postOfficeId);
    }

    @GetMapping("/history")
    List<HistoryDto> getHistoryByParcel(@RequestParam(name = "itemId", required = true) Long itemId) {
        log.info("Полученеие истории премещений посылки");
        return postalItemService.getHistoryByParcel(itemId);
    }

}
