package com.sparta.springmvc.LoginController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginCOntroller {

    @GetMapping("/login")
    public String htmlFile() {  // 정적 웹페이지
        return "redirect:/login-form.html";
    }

    @PostMapping("/login")   //타임리프는 default로 templates폴더의 html파일을 불러옴, 동적 웹페이지
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        Model model) {

        if(id.equals(password)) {
            model.addAttribute("loginId",id);
        }

        return "login-result"; //템플릿 엔진,클라이언트 에 view 전달
    }

}
