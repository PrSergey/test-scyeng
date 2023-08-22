package com.example.skyengtest.service;

import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.repository.AddressRepository;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.skyengtest.excepsion.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    private final AddressRepository addressRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostOfficeDto savePostOffice(PostOfficeDto postOfficeDto) {
        PostOffice postOffice = modelMapper.map(postOfficeDto, PostOffice.class);
        checkIndex(postOffice.getIndex());
        addressRepository.save(postOffice.getAddress());
        PostOffice postOfficeAfterSave = postOfficeRepository.save(postOffice);
        return modelMapper.map(postOfficeAfterSave, PostOfficeDto.class);
    }

    private void checkIndex(String index) {
        if (postOfficeRepository.existsByIndex(index)) {
            throw new ValidationException(String.format("Post office with index=%s already exists", index));
        }
    }

}
