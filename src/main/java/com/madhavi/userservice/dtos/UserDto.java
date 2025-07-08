package com.madhavi.userservice.dtos;

import com.madhavi.userservice.models.Role;
import com.madhavi.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter

public class UserDto {
    private String name;
    private String email;
    private List<String> roles = new ArrayList<>();
    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        userDto.setRoles(new ArrayList<>());
        if (user.getRoles() != null) { // ✅ Safe check added
            for (Role role : user.getRoles()) {
                userDto.getRoles().add(role.getValue());
            }
        }

        return userDto;
    }

}

