package com.example.skyengtest.controller;

import com.example.skyengtest.service.PostalItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class PostalItemController {

    private final PostalItemService postalItemService;




}
