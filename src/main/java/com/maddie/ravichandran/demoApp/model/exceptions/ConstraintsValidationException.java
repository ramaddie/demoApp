package com.maddie.ravichandran.demoApp.model.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ConstraintsValidationException extends RuntimeException {
    private final List<FailureException> failures = new ArrayList<>();

    public ConstraintsValidationException() {}

    public ConstraintsValidationException(FailureException failureException)
    {
        this.failures.add(failureException);
    }

    public ConstraintsValidationException(List<FailureException> failures)
    {
        this.failures.addAll(failures);
    }

    public List<FailureException> getFailures()
    {
        return this.failures;
    }

    public void add(FailureException failureException)
    {
        this.failures.add(failureException);
    }

    public String getMessage()
    {
        return this.failures.toString();
    }
}
