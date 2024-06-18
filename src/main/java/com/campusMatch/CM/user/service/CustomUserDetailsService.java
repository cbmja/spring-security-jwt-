package com.campusMatch.CM.user.service;

import com.campusMatch.CM.user.dto.CustomUserDetails;
import com.campusMatch.CM.user.entity.User;
import com.campusMatch.CM.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        //로그인 성공시
        if(user != null){
            return new CustomUserDetails(user);
        }

        return null;
    }
}