package com.example.skyengtest.service;

import com.example.skyengtest.dto.PostOfficeDto;
import com.example.skyengtest.excepsion.ValidationException;
import com.example.skyengtest.model.Address;
import com.example.skyengtest.model.PostOffice;
import com.example.skyengtest.repository.AddressRepository;
import com.example.skyengtest.repository.PostOfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostOfficeServiceImplTest {

    @Mock
    private PostOfficeRepository postOfficeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    PostOfficeServiceImpl postOfficeService;

    private PostOffice createPostOffice() {
        Address address =new Address(1L, "country", "city", "house number");
        return new PostOffice(1L, "123456", "title", address);
    }

    @Test
    void savePostOffice_whenIndexIsNotUnique_thenValidationException() {
        PostOfficeDto postOfficeDto = new PostOfficeDto();
        PostOffice postOffice = createPostOffice();
        when(modelMapper.map(postOfficeDto, PostOffice.class))
                .thenReturn(postOffice);
        String index = postOffice.getIndex();
        when(postOfficeRepository.existsByIndex(index))
                .thenReturn(true);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> postOfficeService.savePostOffice(postOfficeDto));

        assertEquals(ex.getMessage(), String.format("Post office with index=%s already exists", index));
    }

    @Test
    void savePostOffice_whenDataCorrect_thenReturnPostOfficeDto() {
        PostOfficeDto postOfficeDto = new PostOfficeDto();
        PostOffice postOffice = createPostOffice();
        when(modelMapper.map(postOfficeDto, PostOffice.class))
                .thenReturn(postOffice);
        String index = postOffice.getIndex();
        when(postOfficeRepository.existsByIndex(index))
                .thenReturn(false);
        when(postOfficeRepository.save(postOffice))
                .thenReturn(postOffice);
        when(modelMapper.map(postOffice, PostOfficeDto.class))
                .thenReturn(postOfficeDto);

        PostOfficeDto postOfficeDtoAfterSave = postOfficeService.savePostOffice(postOfficeDto);

        verify(modelMapper, Mockito.times(1))
                .map(postOffice, PostOfficeDto.class);

    }

}