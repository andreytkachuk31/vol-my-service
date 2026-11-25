package com.volmy.dto;

import io.micronaut.core.annotation.Introspected;

import java.sql.Date;

@Introspected
public class DrawDto {

	private Long id;

	private String status;

	private String prize;

	private Date drawDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}
}
