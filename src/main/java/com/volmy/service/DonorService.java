package com.volmy.service;

import com.volmy.converter.DonorConverter;
import com.volmy.dto.DonorDto;
import com.volmy.exception.DonorException;
import com.volmy.exception.DonorNotFoundException;
import com.volmy.repository.DonorRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DonorService {

    @Inject
    private DonorRepository donorRepository;

    @Inject
    private DonorConverter donorConverter;

    public List<DonorDto> getDonors() {
        return donorRepository.findAll()
                .stream()
                .map(this.donorConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<DonorDto> getDonorsByFundraisingId(Long fundraisingId) {
        return donorRepository.findByFundraisingId(fundraisingId)
                .stream()
                .map(this.donorConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public DonorDto getDonorById(Long id) {
        return donorRepository.findById(id)
                .map(this.donorConverter::convertToDto)
                .orElseThrow(() -> new DonorNotFoundException(id));
    }

    public DonorDto createDonor(DonorDto donorDto) {
        return Optional.ofNullable(donorDto)
                .map(this.donorConverter::convertToEntity)
                .map(this.donorRepository::save)
                .map(this.donorConverter::convertToDto)
                .orElseThrow(() -> new DonorException("Failed to create donor"));
    }

    public DonorDto updateDonor(Long id, DonorDto donorDto) {
        donorDto.setId(id);
        return Optional.of(donorDto)
                .map(dto -> this.donorConverter.convertToEntity(id, dto))
                .map(this.donorRepository::update)
                .map(this.donorConverter::convertToDto)
                .orElseThrow(() -> new DonorException("Failed to update donor"));
    }

    public void deleteDonorById(Long id) {
        if (!this.donorRepository.existsById(id)) {
            throw new DonorNotFoundException(id);
        }
        this.donorRepository.deleteById(id);
    }

    public void deleteDonorsByIds(List<Long> ids) {
        this.donorRepository.deleteDonorsByIds(ids);
    }
}
