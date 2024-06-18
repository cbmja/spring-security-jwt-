package com.campusMatch.CM.user.controller;

import com.campusMatch.CM.user.dto.UserDto;
import com.campusMatch.CM.user.service.UserSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {

    private final UserSaveService userSaveService;

    @GetMapping("/")
    public String user(){
        return"user controller";
    }

    @PostMapping("/join")
    public String join(UserDto userDto){

        userSaveService.save(userDto);
        return "join : "+userDto;
    }

}
