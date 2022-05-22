package com.maddie.ravichandran.demoApp.model.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@XmlRootElement
public class User
{
    @NotBlank(message = "firstName is a required field and cannot be blank")
    private String firstName;

    @NotBlank(message = "lastName is a required field and cannot be blank")
    private String lastName;

    @NotBlank(message = "userId is a required field and cannot be blank")
    private String userId;
}
