package com.volmy.controller;

import com.volmy.dto.DonorDto;
import com.volmy.service.DonorService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Controller("donors")
public class DonorController {

    @Inject
    private DonorService donorService;

    @Get
    public List<DonorDto> getDonors(@QueryValue Optional<Long> fundraisingId) {
        return fundraisingId.map(this.donorService::getDonorsByFundraisingId)
                .orElseGet(this.donorService::getDonors);
    }

    @Get("{id}")
    public DonorDto getDonorById(@PathVariable Long id) {
        return this.donorService.getDonorById(id);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public DonorDto createDonor(@Body @Valid DonorDto donorDto) {
        return this.donorService.createDonor(donorDto);
    }

    @Put("{id}")
    public DonorDto updateDonor(@PathVariable Long id, @Body @Valid DonorDto donorDto) {
        return this.donorService.updateDonor(id, donorDto);
    }

    @Delete("{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void deleteDonorById(@PathVariable Long id) {
        this.donorService.deleteDonorById(id);
    }

    @Delete
    @Status(HttpStatus.NO_CONTENT)
    public void deleteDonorsByIds(@Body List<Long> ids) {
        this.donorService.deleteDonorsByIds(ids);
    }
}
