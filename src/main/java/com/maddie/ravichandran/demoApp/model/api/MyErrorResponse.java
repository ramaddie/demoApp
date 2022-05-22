package com.maddie.ravichandran.demoApp.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class MyErrorResponse
{
    private String errorCode;
    private String errorDescription;
    private String errorMessage;
    private List<ValidationError> validationErrors;
    private String errorDetail;
}
