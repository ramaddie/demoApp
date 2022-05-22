package com.maddie.ravichandran.demoApp.model.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MyResponse
{
    private String messageId;
    private String userId;
}
