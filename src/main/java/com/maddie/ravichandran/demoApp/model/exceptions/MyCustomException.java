package com.maddie.ravichandran.demoApp.model.exceptions;

public class MyCustomException extends Exception
{
    public MyCustomException()
    {
    }

    public MyCustomException(String message)
    {
        super(message);
    }

    public MyCustomException(Throwable cause)
    {
        super(cause);
    }

    public MyCustomException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
