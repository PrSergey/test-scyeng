package com.example.skyengtest.service;

import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;

public interface PostOfficeService {

    PostalItemResponseDto saveAddress(PostalItemRequestDto postOfficeDto);

}
