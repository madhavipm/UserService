package com.madhavi.userservice.controllers;


import com.madhavi.userservice.dtos.*;
import com.madhavi.userservice.models.Token;
import com.madhavi.userservice.models.User;
import com.madhavi.userservice.services.UserService;
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
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto){
       User user =  userService.signUp(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPassword());
       return UserDto.from(user);
    }
    @PatchMapping("/logout")
    public void logOut(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());

    }
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable String token){
       User user = userService.ValidateToken(token);
       return UserDto.from(user);
    }
}
