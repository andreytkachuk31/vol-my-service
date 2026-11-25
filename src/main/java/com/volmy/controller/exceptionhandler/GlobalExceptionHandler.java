package com.volmy.controller.exceptionhandler;

import com.volmy.exception.DonorException;
import com.volmy.exception.DonorNotFoundException;
import com.volmy.exception.FundraisingException;
import com.volmy.exception.FundraisingNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import javax.validation.ConstraintViolationException;

@Produces
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<JsonError>> {

    @Override
    public HttpResponse<JsonError> handle(HttpRequest request, Exception exception) {
        if (exception instanceof FundraisingNotFoundException) {
            return handleFundraisingNotFoundException((FundraisingNotFoundException) exception);
        } else if (exception instanceof FundraisingException) {
            return handleFundraisingException((FundraisingException) exception);
        } else if (exception instanceof DonorNotFoundException) {
            return handleDonorNotFoundException((DonorNotFoundException) exception);
        } else if (exception instanceof DonorException) {
            return handleDonorException((DonorException) exception);
        } else {
            return handleConstraintViolationException((ConstraintViolationException) exception);  // Fallback for other RuntimeExceptions
        }
    }

    private HttpResponse<JsonError> handleFundraisingException(FundraisingException ex) {
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonError(ex.getMessage()));
    }

    private HttpResponse<JsonError> handleFundraisingNotFoundException(FundraisingNotFoundException ex) {
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(new JsonError(ex.getMessage()));
    }

    private HttpResponse<JsonError> handleDonorException(DonorException ex) {
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonError(ex.getMessage()));
    }

    private HttpResponse<JsonError> handleDonorNotFoundException(DonorNotFoundException ex) {
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(new JsonError(ex.getMessage()));
    }

    private HttpResponse<JsonError> handleConstraintViolationException(ConstraintViolationException ex) {
        return HttpResponse.status(HttpStatus.BAD_REQUEST).body(new JsonError(ex.getMessage()));
    }
}
