package com.madhavi.userservice.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private ResponseStatus responseStatus;

}
