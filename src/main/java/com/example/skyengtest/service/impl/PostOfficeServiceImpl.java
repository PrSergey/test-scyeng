package com.example.skyengtest.service.impl;

import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostalItemResponseDto saveAddress(PostalItemRequestDto postOfficeDto) {
        PostOffice postOffice = modelMapper.map(postOfficeDto, PostOffice.class);
        PostOffice postOfficeAfterSave = postOfficeRepository.save(postOffice);
        return modelMapper.map(postOfficeAfterSave, PostalItemResponseDto.class);
    }

}
