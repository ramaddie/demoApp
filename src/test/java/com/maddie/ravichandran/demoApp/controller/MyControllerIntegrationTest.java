package com.maddie.ravichandran.demoApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyControllerIntegrationTest
{
    private static final String VALID_JSON_REQUEST = """
            {
              "messageId" : "12345",
              "user" : {
                "firstName" : "Maddie",
                "lastName" : "Ravichandran",
                "userId" : "01"
              }
            }
            """;

    private static final String VALID_XML_REQUEST = """
            <myRequest>
              <messageId>12345</messageId>
              <user>
                <firstName>Maddie</firstName>
                <lastName>Ravichandran</lastName>
                <userId>01</userId>
              </user>
            </myRequest>
            """;

    private static final String MISSING_USER_ID_JSON_REQUEST = """
            {
              "messageId" : "12345",
              "user" : {
                "firstName" : "Maddie",
                "lastName" : "Ravichandran",
                "userId" : ""
              }
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void v1RequestAcceptsJson() throws Exception
    {
        mockMvc.perform(post(MyController.CONTEXT_PATH + MyController.VER_1_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("12345"))
                .andExpect(jsonPath("$.userId").value("01"));
    }

    @Test
    void v1RequestAcceptsXml() throws Exception
    {
        mockMvc.perform(post(MyController.CONTEXT_PATH + MyController.VER_1_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(VALID_XML_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("12345"))
                .andExpect(jsonPath("$.userId").value("01"));
    }

    @Test
    void v1RequestAcceptsFormUrlEncoded() throws Exception
    {
        mockMvc.perform(post(MyController.CONTEXT_PATH + MyController.VER_1_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("messageId", "12345")
                        .param("firstName", "Maddie")
                        .param("lastName", "Ravichandran")
                        .param("userId", "01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("12345"))
                .andExpect(jsonPath("$.userId").value("01"));
    }

    @Test
    void v2RequestAcceptsJsonAsString() throws Exception
    {
        mockMvc.perform(post(MyController.CONTEXT_PATH + MyController.VER_2_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_JSON_REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("12345"))
                .andExpect(jsonPath("$.userId").value("01"));
    }

    @Test
    void v1RequestWithMissingUserIdReturnsValidationError() throws Exception
    {
        mockMvc.perform(post(MyController.CONTEXT_PATH + MyController.VER_1_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MISSING_USER_ID_JSON_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("400"))
                .andExpect(jsonPath("$.validationErrors[0].field").value("user.userId"))
                .andExpect(jsonPath("$.validationErrors[0].errorMessage")
                        .value("userId is a required field and cannot be blank"));
    }
}