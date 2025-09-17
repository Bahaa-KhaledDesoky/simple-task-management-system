package com.example.demo.Mapping;

import com.example.demo.Dtos.Registration;
import com.example.demo.model.AppUser;
import com.example.demo.Dtos.SignIn;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    public AppUser toUser(Registration registration){
        return AppUser.builder()
                .name(registration.name())
                .email(registration.email())
                .password(registration.password())
                .build();
    }
//    public UserDto mapUserDto(AppUser appUser)
//    {
//        return UserDto.builder().
//                username(appUser.getUsername())
//                .phone(appUser.getPhone())
//                .build();
//    }

}
