package com.example.skyengtest.service;

import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.model.PostalItem;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.repository.PostalItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService{

    private final PostOfficeRepository postOfficeRepository;

    private final PostalItemRepository postalItemRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostalItemResponseDto registrationPostalItem(PostalItemRequestDto postalItemDto) {
        PostalItem item = modelMapper.map(postalItemDto, PostalItem.class);
        PostOffice postOffice = postOfficeRepository.findById(postalItemDto.getPostOfficeId())
                .orElseThrow(() -> new RuntimeException());
        item.setPostoffice(postOffice);
        return modelMapper.map(item, PostalItemResponseDto.class);
    }

    @Override
    public PostalItemResponseDto sendingParcel(Long itemId, PostOffice postOffice) {
        return null;
    }

    @Override
    public PostalItemResponseDto acceptParcel(Long itemId, PostOffice postOffice) {
        return null;
    }

    @Override
    public PostalItemResponseDto deliveredParcel(Long itemId, PostOffice postOffice) {
        return null;
    }

    @Override
    public List<HistoryDto> getHistoryByParcel(Long itemId) {
        return null;
    }

}
