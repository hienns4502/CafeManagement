package com.example.CafeManagement.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("activeParent", "trangChu");
        return "index";
    }

    @GetMapping("/403")
    public String show403Page() {
        return "403";
    }
}
