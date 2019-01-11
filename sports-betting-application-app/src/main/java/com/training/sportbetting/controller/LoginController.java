package com.training.sportbetting.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("userCredentials", new UserCredetentialsForm());
        return "login";
    }

    @Data
    class UserCredetentialsForm {
        private String email;
        private String password;
    }
}
