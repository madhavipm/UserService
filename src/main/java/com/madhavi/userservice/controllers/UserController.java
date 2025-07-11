package com.madhavi.userservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.madhavi.userservice.dtos.*;
import com.madhavi.userservice.models.Token;
import com.madhavi.userservice.models.User;
import com.madhavi.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //login, signup, validateToken, logout
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
       User user =  userService.signUp(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPassword());
       return UserDto.from(user);
    }
    @PatchMapping("/logout")
    public void logOut(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());

    }
    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable String token){System.out.println("Validating token: ");
       User user = userService.ValidateToken(token);
        System.out.println("Validating the token");
        if (user == null) {
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //
            }
        }

        return ResponseEntity.ok(UserDto.from(user)); // Return proper On
    }
}
