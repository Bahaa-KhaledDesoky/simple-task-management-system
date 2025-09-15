package com.example.demo.Mapping;

import com.example.demo.model.AppUser;
import com.example.demo.Dtos.SignIn;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    public AppUser toUser(SignIn signIn){
        return AppUser.builder()
                .name(signIn.name())
                .email(signIn.email())
                .password(signIn.password())
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
