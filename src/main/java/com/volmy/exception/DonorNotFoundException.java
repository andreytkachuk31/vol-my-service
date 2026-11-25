package com.volmy.exception;

public class DonorNotFoundException extends DonorException {

	public DonorNotFoundException(Long id) {
		super("Donor [id=" + id + "] was not found");
	}
}
