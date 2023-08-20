package com.example.skyengtest.service.impl;

import com.example.skyengtest.constant.DeliveryStatus;
import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.excepsion.ExistenceException;
import com.example.skyengtest.excepsion.ValidationException;
import com.example.skyengtest.model.History;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.model.PostalItem;
import com.example.skyengtest.repository.HistoryRepository;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.repository.PostalItemRepository;
import com.example.skyengtest.service.PostalItemService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostalItemServiceImpl implements PostalItemService {

    private final PostOfficeRepository postOfficeRepository;

    private final PostalItemRepository postalItemRepository;

    private final ModelMapper modelMapper;

    private final HistoryRepository historyRepository;

    @Override
    public PostalItemResponseDto registrationPostalItem(PostalItemRequestDto postalItemDto) {
        PostalItem item = modelMapper.map(postalItemDto, PostalItem.class);
        item.setId(null);
        long postOfficeId = postalItemDto.getPostOfficeId();
        PostOffice postOffice = postOfficeRepository.findById(postOfficeId)
                .orElseThrow(()
                        -> new ExistenceException(String.format("Post office with id=%d was not found.", postOfficeId)));
        item.setPostOffice(postOffice);
        item.setItemStatus(ItemStatus.ACCEPTED);
        postalItemRepository.save(item);
        addHistory(item, postOffice, DeliveryStatus.ACCEPTED);
        PostalItemResponseDto itemAfterSave = modelMapper.map(item, PostalItemResponseDto.class);
        itemAfterSave.setPostoffice(modelMapper.map(item.getPostOffice(), PostOfficeDto.class));
        return itemAfterSave;
    }

    @Override
    public PostalItemResponseDto sendingParcel(Long itemId, Long postOfficeId) {
        PostalItem item = postalItemRepository.findById(itemId)
                .orElseThrow(()
                        -> new ExistenceException(String.format("Item with id=%d was not found.", itemId)));
        if (item.getItemStatus().equals(ItemStatus.ACCEPTED)) {
            item.setItemStatus(ItemStatus.ON_THE_WAY);
        } else if (!item.getItemStatus().equals(ItemStatus.INTERMEDIATE_POINT)) {
            throw new ValidationException("The item must be located at an intermediate point.");
        }

        PostOffice  postOffice = postOfficeRepository.findById(postOfficeId)
                .orElseThrow(() -> new ExistenceException(String.format("Post office with id=%d was not found.",
                        postOfficeId)));

        item.setItemStatus(ItemStatus.ON_THE_WAY);
        addHistory(item, postOffice, DeliveryStatus.DESPATCHED);
        return modelMapper.map(item, PostalItemResponseDto.class);
    }

    @Override
    public PostalItemResponseDto acceptParcel(Long itemId, Long postOfficeId) {
        PostalItem item = postalItemRepository.findById(itemId).orElseThrow(()
                -> new ExistenceException(String.format("Item with id=%d was not found", itemId)));
        if (!item.getItemStatus().equals(ItemStatus.ON_THE_WAY)) {
            throw new ValidationException("The item must be on the way.");
        }
        PostOffice  postOffice = postOfficeRepository.findById(postOfficeId)
                .orElseThrow(() -> new ExistenceException(String.format("Post office with id=%d was not found.",
                        postOfficeId)));
        if (item.getPostOffice().equals(postOffice)) {
            deliveredParcel(itemId, postOfficeId);
        }

        item.setItemStatus(ItemStatus.INTERMEDIATE_POINT);
        addHistory(item, postOffice, DeliveryStatus.ACCEPTED);
        return modelMapper.map(item, PostalItemResponseDto.class);
    }

    @Override
    public PostalItemResponseDto deliveredParcel(Long itemId, Long postOfficeId) {
        PostalItem item = postalItemRepository.findById(itemId).orElseThrow(()
                -> new ExistenceException(String.format("Item with id=%d was not found", itemId)));
        PostOffice  postOffice = postOfficeRepository.findById(postOfficeId)
                .orElseThrow(() -> new ExistenceException(String.format("Post office with id=%d was not found.",
                        postOfficeId)));
        if (!item.getPostOffice().equals(postOffice)) {
            throw new ValidationException(String
                    .format("Post office with id=%d does not match the item's destination", postOffice.getId()));

        }

        item.setItemStatus(ItemStatus.DELIVERED);
        addHistory(item, postOffice, DeliveryStatus.DELIVERY);
        return modelMapper.map(item, PostalItemResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getHistoryByParcel(Long itemId) {
        List<History> histories = historyRepository.findByItem_Id(itemId);
        return histories.stream()
                .map(h -> modelMapper.map(h, HistoryDto.class))
                .collect(Collectors.toList());
    }

    private History addHistory(PostalItem item, PostOffice office, DeliveryStatus status) {
        History history = new History(item, office, status, LocalDateTime.now());
        return historyRepository.save(history);
    }

}
