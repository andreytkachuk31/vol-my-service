package com.volmy.dto;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Introspected
public class DonorDto {

	private Long id;

	@NotBlank
	private String name;

	@PositiveOrZero
	private Double sum;

	private Long fundraisingId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public @NotBlank String getName() {
		return name;
	}

	public void setName(@NotBlank String name) {
		this.name = name;
	}

	public @PositiveOrZero Double getSum() {
		return sum;
	}

	public void setSum(@PositiveOrZero Double sum) {
		this.sum = sum;
	}

	public @PositiveOrZero Long getFundraisingId() {
		return fundraisingId;
	}

	public void setFundraisingId(@PositiveOrZero Long fundraisingId) {
		this.fundraisingId = fundraisingId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DonorDto donorDto = (DonorDto) o;
		return Objects.equals(id, donorDto.id) && Objects.equals(name, donorDto.name) && Objects.equals(sum, donorDto.sum) && Objects.equals(fundraisingId, donorDto.fundraisingId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, sum, fundraisingId);
	}
}
