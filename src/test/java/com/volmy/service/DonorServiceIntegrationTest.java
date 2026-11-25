package com.volmy.service;

import com.volmy.converter.DonorConverter;
import com.volmy.dto.DonorDto;
import com.volmy.exception.DonorException;
import com.volmy.exception.DonorNotFoundException;
import com.volmy.model.Donor;
import com.volmy.repository.DonorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DonorServiceIntegrationTest {

    private static final long DONOR_ID = 1L;
    private static final long FUNDRAISING_ID = 1L;
    private static final String DONOR_NAME = "Donor name";

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private DonorConverter donorConverter;

    @InjectMocks
    private DonorService donorService;

    @Test
    public void testGetDonors() {
        Donor donor = createDonor();
        DonorDto donorDto = createDonorDto();
        when(donorRepository.findAll()).thenReturn(List.of(donor));
        when(donorConverter.convertToDto(donor)).thenReturn(donorDto);

        List<DonorDto> donorDtos = donorService.getDonors();

        assertEquals(1, donorDtos.size());
    }

    @Test
    public void testGetDonorsByFundraisingId() {
        Donor donor = createDonor();
        DonorDto donorDto = createDonorDto();
        when(donorRepository.findByFundraisingId(FUNDRAISING_ID)).thenReturn(List.of(donor));
        when(donorConverter.convertToDto(donor)).thenReturn(donorDto);

        List<DonorDto> donorDtos = donorService.getDonorsByFundraisingId(FUNDRAISING_ID);

        assertEquals(1, donorDtos.size());
    }

    private Donor createDonor() {
        Donor donor = new Donor();

        donor.setId(DONOR_ID);
        donor.setName(DONOR_NAME);

        return donor;
    }

    private DonorDto createDonorDto() {
        DonorDto donorDto = new DonorDto();

        donorDto.setId(DONOR_ID);
        donorDto.setName(DONOR_NAME);

        return donorDto;
    }
}
