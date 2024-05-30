package org.example.springsecurity.controller;

import org.example.springsecurity.dto.JoinDto;
import org.example.springsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;

    // 회원가입 페이지로 가기
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    // form 으로 데이터를 받아 회원가입 처리 하기
    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto) {
        System.out.println("username: " + joinDto.getUsername());

        joinService.joinProcess(joinDto);

        return "redirect:/join";
    }

}
