package com.maddie.ravichandran.demoApp.model.exceptions;

public class ValidationException extends RuntimeException implements FailureException
{
    private String field;

    public ValidationException(Throwable cause)
    {
        super(cause);
    }

    public ValidationException(String message)
    {
        super(message);
    }

    public ValidationException(String field, String message)
    {
        super(message);
        this.field = field;
    }

    @Override
    public String getDescription() {
        return this.getMessage();
    }

    @Override
    public String getField() {
        return this.field;
    }

    public String toString()
    {
        return "{" + "\"errorMessage\":\"" + this.getDescription() + "\",\"field\":\"" + this.getField() + "\"}";
    }
}
