package com.example.demo.security;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user=userRepository.findByEmail(email).get();
        if (user==null)
        {
            throw new UsernameNotFoundException("User not found with this email : " + email);
        }
        return new CustomUserDetails(user);
    }
}
