package com.example.demoapp.model.exceptions;

public class RequestProcessingException extends Exception
{
    public RequestProcessingException()
    {
    }

    public RequestProcessingException(String message)
    {
        super(message);
    }

    public RequestProcessingException(Throwable cause)
    {
        super(cause);
    }

    public RequestProcessingException(String message, Throwable cause)
    {
        super(message, cause);
    }

}