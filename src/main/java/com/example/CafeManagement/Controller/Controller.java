package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Entity.Employee;
import org.apache.catalina.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new Employee());
        return "signup_form";
    }
}
