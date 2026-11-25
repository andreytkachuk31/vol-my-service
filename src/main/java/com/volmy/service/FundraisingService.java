package com.volmy.service;

import com.volmy.converter.FundraisingConverter;
import com.volmy.dto.FundraisingDto;
import com.volmy.exception.FundraisingException;
import com.volmy.exception.FundraisingNotFoundException;
import com.volmy.model.Donor;
import com.volmy.model.Fundraising;
import com.volmy.repository.DonorRepository;
import com.volmy.repository.FundraisingRepository;
import com.volmy.service.fileparser.FundraisingXLSParser;
import com.volmy.service.importcontext.ImportContext;
import com.volmy.service.importcontext.ImportDetails;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class FundraisingService {

    @Inject
    private FundraisingRepository fundraisingRepository;

    @Inject
    private DonorRepository donorRepository;

    @Inject
    private FundraisingConverter fundraisingConverter;

    @Inject
    private FundraisingXLSParser fundraisingParser;

    @Inject
    private ImportContext importContext;

    @Transactional
    public List<FundraisingDto> getFundraisings() {
        return fundraisingRepository.findAll()
                .stream()
                .map(this.fundraisingConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public FundraisingDto getFundraisingById(Long id) {
        return fundraisingRepository.findById(id)
                .map(this.fundraisingConverter::convertToDto)
                .orElseThrow(() -> new FundraisingNotFoundException(id));
    }

    public FundraisingDto createFundraising(FundraisingDto fundraisingDto) {
        return Optional.ofNullable(fundraisingDto)
                .map(this.fundraisingConverter::convertToEntity)
                .map(this.fundraisingRepository::save)
                .map(this.fundraisingConverter::convertToDto)
                .orElseThrow(() -> new FundraisingException("Failed to create fundraising"));
    }

    public String importFundraisings(CompletedFileUpload file) {
        final String importId = this.importContext.startNew();
        CompletableFuture.runAsync(() -> {
            List<Fundraising> fundraisings = this.fundraisingParser.parse(file, importId);
            this.fundraisingRepository.saveAll(fundraisings);
            this.importContext.complete(importId);
        });
        return importId;
    }

    public void lotteryFundraising(Long id) {
        List<Donor> donors = donorRepository.findByFundraisingId(id);
        int count = 1;
        for (Donor donor : donors) {
            if (donor.getSum() >= 100 && !donor.getName().equals("Неизвестен")
                    && !donor.getName().equals("Ткачук Андрій") && !donor.getName().equals("Христенко Гліб")) {
                int number = (int) (donor.getSum() / 100);
                for (int i = 0; i < number; i++) {
                    System.out.print(count++ + "." + donor.getName());
                    System.out.println();
                }
            }
        }
    }

    public ImportDetails getFundraisingImportStatus(String importId) {
        return this.importContext.get(importId);
    }

    public FundraisingDto updateFundraising(Long id, FundraisingDto fundraisingDto) {
        fundraisingDto.setId(id);
        return Optional.ofNullable(fundraisingDto)
                .map(dto -> this.fundraisingConverter.convertToEntity(id, dto))
                .map(this.fundraisingRepository::update)
                .map(this.fundraisingConverter::convertToDto)
                .orElseThrow(() -> new FundraisingException("Failed to update fundraising"));
    }

    @TransactionalAdvice
    public void deleteFundraisingById(Long id) {
        if (!this.fundraisingRepository.existsById(id)) {
            throw new FundraisingNotFoundException(id);
        }
        this.fundraisingRepository.deleteById(id);
    }

    public void deleteFundraisingsByIds(List<Long> ids) {
        this.fundraisingRepository.deleteFundraisingsByIds(ids);
    }
}
