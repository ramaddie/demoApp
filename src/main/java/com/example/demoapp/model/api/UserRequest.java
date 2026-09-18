package com.example.demoapp.model.api;

import lombok.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@XmlRootElement
public class UserRequest
{
    @NotBlank(message = "messageId is a required field and cannot be blank")
    private String messageId;

    @NotNull(message = "user is required required field")
    @Valid
    private User user;
}