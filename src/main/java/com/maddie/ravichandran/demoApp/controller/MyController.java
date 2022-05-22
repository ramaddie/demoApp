package com.maddie.ravichandran.demoApp.controller;

import com.maddie.ravichandran.demoApp.model.api.MyRequest;
import com.maddie.ravichandran.demoApp.model.api.MyResponse;
import com.maddie.ravichandran.demoApp.model.api.User;
import com.maddie.ravichandran.demoApp.model.exceptions.MyCustomException;
import com.maddie.ravichandran.demoApp.service.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(MyController.CONTEXT_PATH)
@Slf4j
@RequiredArgsConstructor
public class MyController
{
    public static final String CONTEXT_PATH = "/demoAPI";
    public static final String VER_1_REQUEST_PATH = "/v1/request";
    public static final String VER_2_REQUEST_PATH = "/v2/request";

    @Autowired
    RequestHandler requestHandler;

    @PostMapping(value = VER_2_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MyResponse postRequestAsString(@RequestBody String jsonRequest)
            throws MyCustomException
    {
        log.info("Incoming Request was JSON, using ver 2 handling!");
        return requestHandler.handleIncomingRequestString(jsonRequest);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MyResponse postRequestAsString(@RequestBody @Valid MyRequest request)
            throws MyCustomException
    {
        log.info("Incoming Request was JSON, using ver 1 handling!");
        return requestHandler.generateResponse(request);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MyResponse postRequestXML(@RequestBody @Valid MyRequest request)
            throws MyCustomException
    {
        log.info("Incoming Request was XML!");
        return requestHandler.generateResponse(request);
    }

    @PostMapping(value = VER_1_REQUEST_PATH,
            consumes =  MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MyResponse postRequestFormURLEncoded(@RequestParam String messageId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String userId)
            throws MyCustomException
    {
        log.info("Incoming Request was x-www-form-urlencoded!");
        return requestHandler.handleIncomingRequestFormUrlEncoded(messageId, firstName, lastName, userId);
    }

}
