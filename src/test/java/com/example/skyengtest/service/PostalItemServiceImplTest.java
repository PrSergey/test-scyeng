package com.example.skyengtest.service;

import com.example.skyengtest.constant.ItemStatus;
import com.example.skyengtest.constant.ItemType;
import com.example.skyengtest.dto.HistoryDto;
import com.example.skyengtest.dto.PostalItemRequestDto;
import com.example.skyengtest.dto.PostalItemResponseDto;
import com.example.skyengtest.excepsion.ExistenceException;
import com.example.skyengtest.excepsion.ValidationException;
import com.example.skyengtest.model.Address;
import com.example.skyengtest.model.History;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.model.PostalItem;
import com.example.skyengtest.repository.HistoryRepository;
import com.example.skyengtest.repository.PostOfficeRepository;
import com.example.skyengtest.repository.PostalItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    private  PostOffice createPostOffice() {
        return new PostOffice(1L, "xxxxxx", "title", new Address());
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
    void sendingParcel_whenItemStatusIsAccept_thenReturnPostalItemResponseDto() {
        PostOffice postOffice = createPostOffice();
        PostalItem postalItem = new PostalItem(1L, ItemType.POSTCARD, postOffice,
                ItemStatus.ACCEPTED, "Ban");
        when(postalItemRepository.findById(any()))
                .thenReturn(Optional.of(postalItem));
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.of(postOffice));

        PostalItemResponseDto postalItemResponseDto = new PostalItemResponseDto();
        when(modelMapper.map(postalItem, PostalItemResponseDto.class))
                .thenReturn(postalItemResponseDto);

        postalItemService.sendingParcel(postalItem.getId(), postOffice.getId());
        postalItem.setItemStatus(ItemStatus.ON_THE_WAY);
        verify(modelMapper, Mockito.times(1))
                .map(postalItem, PostalItemResponseDto.class);

    }

    @Test
    void sendingParcel_whenItemStatusIsOnTheWay_thenValidationException() {
        PostOffice postOffice = createPostOffice();
        PostalItem postalItem = new PostalItem(1L, ItemType.POSTCARD, postOffice,
                ItemStatus.ON_THE_WAY, "Ban");
        when(postalItemRepository.findById(any()))
                .thenReturn(Optional.of(postalItem));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                postalItemService.sendingParcel(postalItem.getId(), postOffice.getId()));

        assertEquals(ex.getMessage(), "The item must be located at in intermediate point.");

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
    void deliveredParcel_whenPostOfficeIdIsIncorrect_thenReturnValidationException() {
        PostOffice postOffice = createPostOffice();
        PostalItem postalItem = new PostalItem(1L, ItemType.POSTCARD, postOffice,
                ItemStatus.ON_THE_WAY, "Ban");
        PostOffice postOfficeIntermediate = createPostOffice();
        postOfficeIntermediate.setId(2);
        when(postalItemRepository.findById(any()))
                .thenReturn(Optional.of(postalItem));
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.of(postOfficeIntermediate));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> postalItemService.deliveredParcel(postalItem.getId(), postOfficeIntermediate.getId()));

        assertEquals(ex.getMessage(),
                String.format("Post office with id=%d does not match the item's destination", postOfficeIntermediate.getId()));
    }

    @Test
    void deliveredParcel_whenItemIdAndPostOfficeIdIsCorrect_thenReturnPostalItemResponseDto() {
        PostOffice postOffice = createPostOffice();
        PostalItem postalItem = new PostalItem(1L, ItemType.POSTCARD, postOffice,
                ItemStatus.ON_THE_WAY, "Ban");
        when(postalItemRepository.findById(any()))
                .thenReturn(Optional.of(postalItem));
        when(postOfficeRepository.findById(any()))
                .thenReturn(Optional.of(postOffice));
        PostalItemResponseDto postalItemResponseDto = new PostalItemResponseDto();
        postalItemResponseDto.setId(1L);
        when(modelMapper.map(postalItem, PostalItemResponseDto.class))
                .thenReturn(postalItemResponseDto);

        PostalItemResponseDto resultPostalItem = postalItemService.deliveredParcel(postalItem.getId(),
                postOffice.getId());

        verify(modelMapper, Mockito.times(1))
                .map(postalItem, PostalItemResponseDto.class);
        assertEquals(postalItem.getId(), resultPostalItem.getId());
    }

    @Test
    void getHistoryByParcel_whenCorrectData_thenReturnList() {
        List<History> histories = new ArrayList<>();
        Long itemId = 1L;
        when(historyRepository.findByItem_Id(itemId))
                .thenReturn(histories);

        List<HistoryDto> historiesDto = postalItemService.getHistoryByParcel(itemId);

        assertEquals(historiesDto.size(), histories.size());
    }





}





























