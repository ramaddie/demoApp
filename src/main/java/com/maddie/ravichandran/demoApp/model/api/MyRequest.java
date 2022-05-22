package com.maddie.ravichandran.demoApp.model.api;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@XmlRootElement
public class MyRequest
{
    @NotBlank(message = "messageId is a required field and cannot be blank")
    private String messageId;

    @NotNull(message = "user is required required field")
    @Valid
    private User user;
}
