package com.example.demoapp.model.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResponse
{
    private String messageId;
    private String userId;
}