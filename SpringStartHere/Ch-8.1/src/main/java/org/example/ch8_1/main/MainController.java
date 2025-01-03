package org.example.ch8_1.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/home")
    public String home(Model page) {
        page.addAttribute("username", "Marcus");
        page.addAttribute("color", "red");
        return "home";
    }
}
