package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.RoleDb;
import propensi.tugas.pebelco.service.RoleService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class UserController {
    @Autowired
    private RoleDb roleDb;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping(value = "/user/register")
    public String addUser(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("user", new UserModel());
        return "user/register";
    }

    @PostMapping("/user/register")
    public String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        try {
            if (userService.getUserbyEmail(user.getEmail()) == null) {
                userService.addUser(user);
                return "redirect:/login";
            } else {
                model.addAttribute("msg", "Email Invalid");
                model.addAttribute("listRole", roleService.findAll());
                model.addAttribute("user", new UserModel());
                return "user/register";
            }    
        } catch (Exception e) {
            model.addAttribute("listRole", roleService.findAll());
            model.addAttribute("msg", "Email Invalid on Database");
            model.addAttribute("user", new UserModel());
            return "user/register"; 
        }
        
    }
}
