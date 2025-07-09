package com.madhavi.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SendEmailEventDto {
    private String email;
    private String subject;
    private String body;
}
