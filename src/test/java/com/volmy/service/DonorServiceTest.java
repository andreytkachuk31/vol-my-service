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

@ExtendWith(MockitoExtension.class)
class DonorServiceTest {

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

    @Test
    public void testGetDonorById() {
        Donor donor = createDonor();
        DonorDto expectedDonorDto = createDonorDto();
        when(donorRepository.findById(DONOR_ID)).thenReturn(Optional.of(donor));
        when(donorConverter.convertToDto(donor)).thenReturn(expectedDonorDto);

        DonorDto actualDonorDto = donorService.getDonorById(DONOR_ID);

        assertEquals(expectedDonorDto, actualDonorDto);
    }

    @Test
    public void testGetDonorByIdIfNotFound() {
        when(donorRepository.findById(DONOR_ID)).thenReturn(Optional.empty());

        assertThrows(DonorNotFoundException.class, () -> donorService.getDonorById(DONOR_ID));
    }

    @Test
    public void testCreateDonor() {
        Donor donor = createDonor();
        DonorDto donorDto = createDonorDto();
        when(donorConverter.convertToEntity(donorDto)).thenReturn(donor);
        when(donorRepository.save(donor)).thenReturn(donor);
        when(donorConverter.convertToDto(donor)).thenReturn(donorDto);

        donorService.createDonor(donorDto);

        verify(donorRepository).save(donor);
    }

    @Test
    public void testCreateDonorIfNotFound() {
        DonorDto donorDto = createDonorDto();
        when(donorConverter.convertToEntity(donorDto)).thenReturn(null);

        assertThrows(DonorException.class, () -> donorService.createDonor(donorDto));
    }

    @Test
    public void testUpdateDonor() {
        Donor donor = createDonor();
        DonorDto donorDto = createDonorDto();
        when(donorConverter.convertToEntity(DONOR_ID, donorDto)).thenReturn(donor);
        when(donorRepository.update(donor)).thenReturn(donor);
        when(donorConverter.convertToDto(donor)).thenReturn(donorDto);

        donorService.updateDonor(DONOR_ID, donorDto);

        verify(donorRepository).update(donor);
    }

    @Test
    public void testUpdateDonorIfNotFound() {
        DonorDto donorDto = createDonorDto();
        when(donorConverter.convertToEntity(DONOR_ID, donorDto)).thenReturn(null);

        assertThrows(DonorException.class, () -> donorService.updateDonor(DONOR_ID, donorDto));
    }

    @Test
    public void testDeleteDonorById() {
        when(donorRepository.existsById(DONOR_ID)).thenReturn(true);

        donorService.deleteDonorById(DONOR_ID);

        verify(this.donorRepository).deleteById(DONOR_ID);
    }

    @Test
    public void testDeleteDonorByIdIfNotFound() {
        when(donorRepository.existsById(DONOR_ID)).thenReturn(false);

        assertThrows(DonorNotFoundException.class, () -> donorService.deleteDonorById(DONOR_ID));
    }   

    @Test
    public void testDeleteDonorsByIds() {
        donorService.deleteDonorsByIds(List.of(DONOR_ID));

        verify(this.donorRepository).deleteDonorsByIds(List.of(DONOR_ID));
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
