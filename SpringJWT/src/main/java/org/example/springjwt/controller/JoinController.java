package org.example.springjwt.controller;

import lombok.RequiredArgsConstructor;
import org.example.springjwt.dto.JoinDTO;
import org.example.springjwt.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        joinService.join(joinDTO);
        return "ok";
    }

}
