package com.example.demo.Services;

import com.example.demo.Dtos.LogInRequest;
import com.example.demo.Dtos.SignIn;
import com.example.demo.Exceptions.UserExistException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Exceptions.WrongPasswordExceprion;
import com.example.demo.Mapping.UserMapping;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.AppUser;
import com.example.demo.Dtos.TokenResponse;
import com.example.demo.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public AppUser getUser(HttpServletRequest request)
    {
        String jwt =jwtUtils.getJwFromHeader(request);
        String email=jwtUtils.extractEmail(jwt);
        AppUser user=findUserByEmail(email);
        return user;
    }
    @Override
    public Integer signUp(SignIn signIn) {
        try {
            if (userExist(signIn.email())) {
                throw new UserExistException();
            }
            AppUser user = userMapping.toUser(signIn);
            user.setPassword(hashPassword(user.getPassword()));
            return userRepository.save(user).getId();
        }
        catch (UserExistException e)
        {
            throw new UserExistException();
        }
        catch (RuntimeException e)
        {
            throw new  RuntimeException();
        }

    }
    @Override
    public boolean userExist(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }
    @Override
    public AppUser findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
    }
    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
    }
    @Override
    public TokenResponse login(LogInRequest logInRequest) {

        try {
            AppUser user=findUserByEmail(logInRequest.email());
            if(!checkPassword(logInRequest.password(),user.getPassword()))
                throw new WrongPasswordExceprion();
            TokenResponse tokenResponse=authService.getRefreshToken(user.getEmail());
            user.setRefreshToken(tokenResponse.refreshToken());
            userRepository.save(user);
            return tokenResponse;
        }
        catch(UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        catch (WrongPasswordExceprion e)
        {
            throw new WrongPasswordExceprion();
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException();
        }

    }
    @Override
    public boolean logOut(Integer id){

        try {
            AppUser user=findUserById(id);
            user.setRefreshToken(null);
            userRepository.save(user);
            return true;
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }

    }
    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
