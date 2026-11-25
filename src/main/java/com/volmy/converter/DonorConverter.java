package com.volmy.converter;

import com.volmy.dto.DonorDto;
import com.volmy.model.Donor;
import com.volmy.model.Fundraising;
import jakarta.inject.Singleton;

@Singleton
public class DonorConverter {

    public DonorDto convertToDto(Donor donor) {
        DonorDto donorDto = new DonorDto();

        donorDto.setId(donor.getId());
        donorDto.setName(donor.getName());
        donorDto.setSum(donor.getSum());
        donorDto.setFundraisingId(donor.getFundraising().getId());

        return donorDto;
    }

    public Donor convertToEntity(DonorDto donorDto) {
        return convertToEntity(null, donorDto);
    }

    public Donor convertToEntity(Long id, DonorDto donorDto) {
        Donor donor = new Donor();

        if (id != null) {
            donor.setId(id);
        }
        donor.setName(donorDto.getName());
        donor.setSum(donorDto.getSum());
        donor.setFundraising(initFundraising(donorDto));

        return donor;
    }

    private static Fundraising initFundraising(DonorDto donorDto) {
        Fundraising fundraising = new Fundraising();

        fundraising.setId(donorDto.getFundraisingId());

        return fundraising;
    }
}
