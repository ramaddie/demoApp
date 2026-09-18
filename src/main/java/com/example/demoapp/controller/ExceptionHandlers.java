package com.example.demoapp.controller;

import com.example.demoapp.model.api.ErrorResponse;
import com.example.demoapp.model.api.ValidationError;
import com.example.demoapp.model.exceptions.ConstraintsValidationException;
import com.example.demoapp.model.exceptions.FailureException;
import com.example.demoapp.model.exceptions.RequestProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlers
{

    @ExceptionHandler(RequestProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    private ErrorResponse handleRequestProcessingException(RequestProcessingException e)
    {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
                .errorDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .errorMessage("An error occurred while processing the request")
                .errorDetail(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<ValidationError> errors = new ArrayList<>();

        for (FieldError error : result.getFieldErrors())
        {
            errors.add(ValidationError.builder()
                    .errorMessage(error.getDefaultMessage())
                    .field(error.getField())
                    .build());
        }

        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value() + "")
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage("Fix all errors in request before retrying")
                .validationErrors(errors)
                .build();
    }

    @ExceptionHandler(ConstraintsValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleConstraintsValidationException(ConstraintsValidationException e) {

        List<ValidationError> errors = new ArrayList<>();

        for (FailureException error : e.getFailures())
        {
            errors.add(ValidationError.builder()
                    .errorMessage(error.getDescription())
                    .field(error.getField())
                    .build());
        }

        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value() + "")
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage("Fix all errors in request before retrying")
                .validationErrors(errors)
                .build();
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageConversionException(HttpMessageConversionException e)
    {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value() + "")
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage("Unable to read message request")
                .errorDetail(e.getRootCause() == null ? e.getMessage() : e.getRootCause().getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    private ErrorResponse handleGenericException(Exception e)
    {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
                .errorDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .errorMessage("Unexpected exception was thrown")
                .errorDetail(e.getMessage())
                .build();
    }

}