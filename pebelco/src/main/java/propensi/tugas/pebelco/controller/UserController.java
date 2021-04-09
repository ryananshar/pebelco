package propensi.tugas.pebelco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
}
