package com.example.demoapp.controller;

import com.example.demoapp.model.api.UserRequest;
import com.example.demoapp.model.api.UserResponse;
import com.example.demoapp.model.api.User;
import com.example.demoapp.model.exceptions.RequestProcessingException;
import com.example.demoapp.service.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(UserRequestController.CONTEXT_PATH)
@Slf4j
@RequiredArgsConstructor
public class UserRequestController
{
    public static final String CONTEXT_PATH = "/demoAPI";
    public static final String VER_1_REQUEST_PATH = "/v1/request";
    public static final String VER_2_REQUEST_PATH = "/v2/request";

    @Autowired
    RequestHandler requestHandler;

    @PostMapping(value = VER_2_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse postRequestAsString(@RequestBody String jsonRequest)
            throws RequestProcessingException
    {
        log.info("Incoming Request was JSON, using ver 2 handling!");
        return requestHandler.handleIncomingRequestString(jsonRequest);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse postRequestAsString(@RequestBody @Valid UserRequest request)
            throws RequestProcessingException
    {
        log.info("Incoming Request was JSON, using ver 1 handling!");
        return requestHandler.generateResponse(request);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse postRequestXML(@RequestBody @Valid UserRequest request)
            throws RequestProcessingException
    {
        log.info("Incoming Request was XML!");
        return requestHandler.generateResponse(request);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse postRequestFormURLEncoded(@RequestParam String messageId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String userId)
            throws RequestProcessingException
    {
        log.info("Incoming Request was x-www-form-urlencoded!");
        return requestHandler.handleIncomingRequestFormUrlEncoded(messageId, firstName, lastName, userId);
    }

}