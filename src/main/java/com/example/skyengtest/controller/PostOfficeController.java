package com.example.skyengtest.controller;

import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.service.PostOfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/office")
@Slf4j
@RequiredArgsConstructor
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PostOfficeDto registrationPostalItem(
            @RequestBody @Valid PostOfficeDto postOfficeDto) {
        log.info("Запрос на добавление адреса в базу");
        return postOfficeService.savePostOffice(postOfficeDto);
    }

}
