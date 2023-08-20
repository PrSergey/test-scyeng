package com.example.skyengtest.service.impl;

import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.constant.ItemType;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.excepsion.ExistenceException;
import com.example.skyengtest.excepsion.ValidationException;
import com.example.skyengtest.model.Address;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.model.PostalItem;
import com.example.skyengtest.repository.HistoryRepository;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.repository.PostalItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostalItemServiceImplTest {

    @Mock
    private PostOfficeRepository postOfficeRepository;
    @Mock
    private PostalItemRepository postalItemRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    private HistoryRepository historyRepository;
    @InjectMocks
    PostalItemServiceImpl postalItemService;

    private PostalItemRequestDto createPostalItemRequestDto() {
        return new PostalItemRequestDto(ItemType.LETTER, 1L, "Ban");
    }

    @Test
    void registration_whenPostOfficeWasNotFound_themExistenceException() {
        PostalItemRequestDto postalItemRequestDto = createPostalItemRequestDto();
        PostalItem item = new PostalItem();
        when(modelMapper.map(postalItemRequestDto, PostalItem.class))
                .thenReturn(item);
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.empty());

        ExistenceException ex = assertThrows(ExistenceException.class,
                () -> postalItemService.registrationPostalItem(postalItemRequestDto));

        assertEquals(ex.getMessage(), String.format("Post office with id=%d was not found.",
                postalItemRequestDto.getPostOfficeId()));
    }

    @Test
    void registration_whenPostOfficeWasValid_themReturnPostalItemResponseDto() {
        PostalItemRequestDto postalItemRequestDto = createPostalItemRequestDto();
        PostalItem item = new PostalItem();
        when(modelMapper.map(postalItemRequestDto, PostalItem.class))
                .thenReturn(item);
        PostOffice postOffice = new PostOffice();
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.of(postOffice));
        when(postalItemRepository.save(item))
                .thenReturn(item);
        PostalItemResponseDto itemAfterSave = new PostalItemResponseDto();
        when(modelMapper.map(item, PostalItemResponseDto.class))
                .thenReturn(itemAfterSave);

        assertEquals(postalItemService.registrationPostalItem(postalItemRequestDto),
                itemAfterSave);
    }

    @Test
    void acceptParcel_whenItemStatusIsNotOnTheWay_thenValidationException() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1L);
        Long id = postalItem.getId();
        Long postOfficeId = 1L;
        when(postalItemRepository.findById(id))
                .thenReturn(Optional.of(postalItem));
        postalItem.setItemStatus(ItemStatus.INTERMEDIATE_POINT);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> postalItemService.acceptParcel(id, postOfficeId));

        assertEquals(ex.getMessage(), "The item must be on the way.");
    }

    @Test
    void acceptParcel_whenItemStatusIsCorrect_thenReturnPostalItemResponseDto() {
        PostOffice postOffice = new PostOffice(1L, "xxxxxx", "title", new Address());
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1L);
        Long itemId = postalItem.getId();
        postalItem.setPostOffice(postOffice);
        when(postalItemRepository.findById(itemId))
                .thenReturn(Optional.of(postalItem));
        postalItem.setItemStatus(ItemStatus.ON_THE_WAY);
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.of(postOffice));
        PostalItemResponseDto itemAfterSave = new PostalItemResponseDto();
        when(modelMapper.map(postalItem, PostalItemResponseDto.class))
                .thenReturn(itemAfterSave);


        assertEquals(postalItemService.acceptParcel(itemId, postOffice.getId()),
                itemAfterSave);
    }

    @Test
    void deliveredParcel_whenItemStatusIsCorrect_thenReturnPostalItemResponseDto() {

    }



}