package com.volmy.controller;

import com.volmy.dto.FundraisingDto;
import com.volmy.service.FundraisingService;
import com.volmy.service.importcontext.ImportDetails;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

import javax.validation.Valid;
import java.util.List;

@Validated
@Controller(value="fundraisings", port = "8081")
public class FundraisingController {

	@Inject
	private FundraisingService fundraisingService;

	@Get
	public List<FundraisingDto> getFundraisings() {
		return this.fundraisingService.getFundraisings();
	}

	@Get("{id}")
	public FundraisingDto getFundraisingById(@PathVariable Long id) {
		return this.fundraisingService.getFundraisingById(id);
	}

	@Post
	@Status(HttpStatus.CREATED)
	public FundraisingDto createFundraising(@Body @Valid FundraisingDto fundraisingDto) {
		return this.fundraisingService.createFundraising(fundraisingDto);
	}

	@Post(value = "import", consumes = MediaType.MULTIPART_FORM_DATA)
	@Status(HttpStatus.OK)
	public String importFundraisings(CompletedFileUpload file) {
		return this.fundraisingService.importFundraisings(file);
	}

	@Get("status/{importId}")
	public ImportDetails getFundraisingImportStatus(@PathVariable String importId) {
		return this.fundraisingService.getFundraisingImportStatus(importId);
	}

	@Post(value = "{id}/lottery")
	@Status(HttpStatus.OK)
	public void lotteryFundraising(@PathVariable Long id) {
		this.fundraisingService.lotteryFundraising(id);
	}

	@Put("{id}")
	public FundraisingDto updateFundraising(@PathVariable Long id, @Body @Valid FundraisingDto fundraisingDto) {
		return this.fundraisingService.updateFundraising(id, fundraisingDto);
	}

	@Delete("{id}")
	@Status(HttpStatus.NO_CONTENT)
	public void deleteFundraisingById(@PathVariable Long id) {
		this.fundraisingService.deleteFundraisingById(id);
	}

	@Delete
	@Status(HttpStatus.NO_CONTENT)
	public void deleteFundraisingsByIds(@Body List<Long> ids) {
		this.fundraisingService.deleteFundraisingsByIds(ids);
	}
}
