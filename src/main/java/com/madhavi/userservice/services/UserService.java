package com.madhavi.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.madhavi.userservice.models.Token;
import com.madhavi.userservice.models.User;

public interface UserService {
    User signUp(String name, String email, String password) throws JsonProcessingException;
    Token login(String email , String password);
    User ValidateToken(String tokenValue);
    void logout(String value);

}
