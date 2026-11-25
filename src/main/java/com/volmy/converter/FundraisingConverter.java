package com.volmy.converter;

import com.volmy.dto.DrawDto;
import com.volmy.dto.FundraisingDto;
import com.volmy.model.Draw;
import com.volmy.model.Fundraising;
import com.volmy.util.CommonUtil;
import jakarta.inject.Singleton;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.volmy.util.CommonUtil.calculatePercentage;

@Singleton
public class FundraisingConverter {

	public FundraisingDto convertToDto(Fundraising fundraising) {
		FundraisingDto fundraisingDto = new FundraisingDto();

		fundraisingDto.setId(fundraising.getId());
		fundraisingDto.setName(fundraising.getName());
		fundraisingDto.setDescription(fundraising.getDescription());
		fundraisingDto.setImage(fundraising.getImage());
		fundraisingDto.setTag(fundraising.getTag());
		fundraisingDto.setStatus(fundraising.getStatus());
		fundraisingDto.setProgressPercent(fundraising.getProgressPercent());
		fundraisingDto.setCurrentSum(fundraising.getCurrentSum());
		fundraisingDto.setTotalSum(fundraising.getTotalSum());
		fundraisingDto.setStartDate(CommonUtil.toLocalDate(fundraising.getStartDate()));
		fundraisingDto.setEndDate(CommonUtil.toLocalDate(fundraising.getEndDate()));
		fundraisingDto.setDraw(convertToDrawDto(fundraising.getDraws()));

		return fundraisingDto;
	}

	public Fundraising convertToEntity(FundraisingDto fundraisingDto) {
		return convertToEntity(null, fundraisingDto);
	}

	public Fundraising convertToEntity(Long id, FundraisingDto fundraisingDto) {
		Fundraising fundraising = new Fundraising();

		if (id != null) {
			fundraising.setId(id);
		}
		fundraising.setName(fundraisingDto.getName());
		fundraising.setDescription(fundraisingDto.getDescription());
		fundraising.setImage(fundraisingDto.getImage());
		fundraising.setTag(fundraisingDto.getTag());
		String status = fundraisingDto.getStatus();
		fundraising.setStatus(status);
		switch(status) {
			case "Not Started": {
				fundraising.setProgressPercent(0);
				fundraising.setCurrentSum(0.0);
				break;
			}
			case "In progress": {
				fundraising.setProgressPercent(calculatePercentage(fundraisingDto.getCurrentSum(), fundraisingDto.getTotalSum()));
				fundraising.setCurrentSum(fundraisingDto.getCurrentSum());
				break;
			}
			case "Completed": {
				fundraising.setProgressPercent(calculatePercentage(fundraisingDto.getCurrentSum(), fundraisingDto.getTotalSum()));
				fundraising.setCurrentSum(Optional.ofNullable(fundraisingDto.getCurrentSum()).orElse(fundraisingDto.getTotalSum()));
				break;
			}
		}
		fundraising.setTotalSum(fundraisingDto.getTotalSum());
		fundraising.setStartDate(CommonUtil.toDate(fundraisingDto.getStartDate()));
		fundraising.setEndDate(CommonUtil.toDate(fundraisingDto.getEndDate()));

		return fundraising;
	}


	private DrawDto convertToDrawDto(List<Draw> draws) {
		DrawDto drawDto = null;

		if (CollectionUtils.isNotEmpty(draws)) {
			drawDto = new DrawDto();
			Draw draw = draws.get(0);
			drawDto.setId(draw.getId());
			drawDto.setStatus(draw.getStatus());
			drawDto.setPrize(draw.getPrize());
			drawDto.setDrawDate(draw.getDrawDate());
		}

		return drawDto;
	}
}
