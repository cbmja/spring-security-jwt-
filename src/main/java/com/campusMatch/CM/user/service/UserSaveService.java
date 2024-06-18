package com.campusMatch.CM.user.service;

import com.campusMatch.CM.user.dto.UserDto;
import com.campusMatch.CM.user.entity.User;
import com.campusMatch.CM.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void save(UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        if(userRepository.findByUsername(username) == null){
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(userDto.getEmail());
            if(userDto.getEmail().equals("cbmja@naver.com")){
                user.setRole("ROLE_ADMIN");
            }else{
                user.setRole("ROLE_USER");
            }

            userRepository.save(user);
        }
    }

}
