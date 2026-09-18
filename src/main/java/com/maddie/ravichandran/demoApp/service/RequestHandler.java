package com.maddie.ravichandran.demoApp.service;

import com.maddie.ravichandran.demoApp.model.api.MyRequest;
import com.maddie.ravichandran.demoApp.model.api.MyResponse;
import com.maddie.ravichandran.demoApp.model.api.User;
import com.maddie.ravichandran.demoApp.model.exceptions.ConstraintsValidationException;
import com.maddie.ravichandran.demoApp.model.exceptions.MyCustomException;
import com.maddie.ravichandran.demoApp.model.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class RequestHandler
{
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private ObjectMapper objectMapper;

    public boolean isEmptyString(String str)
    {
        return str == null || "".equals(str) || str.strip().length() == 0;
    }

    public void prettyPrint(Object obj)
    {
        try
        {
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        }
        catch (JacksonException ex)
        {
            log.warn("Unable to pretty print object of type {}", obj.getClass(), ex);
        }
    }

    public MyResponse handleIncomingRequestString(String request)
            throws MyCustomException
    {
        return getMyRequest(request);
    }

    public MyResponse handleIncomingRequestFormUrlEncoded(String messageId, String firstName, String lastName, String userId)
            throws MyCustomException
    {
        MyRequest request = MyRequest.builder()
                .messageId(messageId)
                .user(User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .userId(userId)
                        .build())
                .build();
        checkAndThrowValidationErrors(request);
        return generateResponse(request);
    }

    private MyResponse getMyRequest(String jsonString)
            throws MyCustomException
    {
        try {
            MyRequest request = objectMapper.readValue(jsonString, MyRequest.class);
            checkAndThrowValidationErrors(request);
            return generateResponse(request);
        }
        catch (JacksonException ex)
        {
            throw new MyCustomException("Error deserializing incoming string request!");
        }
    }

    private void checkAndThrowValidationErrors(MyRequest request)
    {
        Set<ConstraintViolation<MyRequest>> fieldErrors = VALIDATOR.validate(request);
        if (!fieldErrors.isEmpty())
        {
            ConstraintsValidationException exception = new ConstraintsValidationException();

            fieldErrors.forEach(fe -> {
                final String dummyField = getFieldPath(fe);
                exception.add(new ValidationException(dummyField, fe.getMessage()));
            });

            throw exception;
        }
    }

    private static <T> String getFieldPath(ConstraintViolation<T> error)
    {
        return StreamSupport.stream(error.getPropertyPath().spliterator(), false)
                .map(Path.Node::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("."));
    }

    public MyResponse generateResponse(@Valid MyRequest request)
            throws MyCustomException
    {
        prettyPrint(request);

        return MyResponse.builder()
                .messageId(request.getMessageId())
                .userId(request.getUser().getUserId())
                .build();
    }
}
