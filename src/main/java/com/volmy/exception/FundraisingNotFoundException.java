package com.volmy.exception;

public class FundraisingNotFoundException extends FundraisingException {

	public FundraisingNotFoundException(Long id) {
		super("Fundraising [id=" + id + "] was not found");
	}
}
