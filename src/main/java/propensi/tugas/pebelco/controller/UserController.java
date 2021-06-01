package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.NotifikasiDb;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.RoleService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class UserController {
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired NotifikasiDb notifikasiDb;

    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("user", new UserModel());
        return "user/login-v2";
    }

    @GetMapping(value = "/profile")
    public String profileUser(Model model){

        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("user", user);
        return "user/profile";

    }

    @RequestMapping(value = "/profile/update-pass", method = RequestMethod.POST)
    private String updatePassword(@RequestParam String email,
                                  String oldPass,
                                  String newPass,
                                  String newPassConfirm,
                                  Model model){
        UserModel user = userService.getUserbyEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPass, user.getPassword())){
            if(newPass.equals(newPassConfirm)){
                userService.changePassword(user, newPass);
                model.addAttribute("pop", "green");
                model.addAttribute("subMsg", "");
                model.addAttribute("user", user);
                model.addAttribute("msg", "Password Berhasil Diubah");
            }else{
                model.addAttribute("pop", "red");
                model.addAttribute("subMsg", "Konfirmasi password baru tidak valid");
                model.addAttribute("user", user);
                model.addAttribute("msg", "Password Gagal Diubah");
            }
        } else {
            model.addAttribute("pop", "red");
            model.addAttribute("subMsg", "Password lama tidak valid");
            model.addAttribute("user", user);
            model.addAttribute("msg", "Password Gagal Diubah");
        }

        return "user/profile";
    }

    @GetMapping(value = "/user/register")
    public String addUser(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("user", new UserModel());
        return "user/login-v2";
    }

    @PostMapping("/user/register")
    public String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        try {
            if (userService.getUserbyEmail(user.getEmail()) == null) {
                userService.addUser(user);
                // user.setListNotifikasi(new ArrayList<NotifikasiModel>());
                // user.setListLaporanStafSales(new ArrayList<LaporanStafSalesModel>());
                return "redirect:/login";
            } else {
                model.addAttribute("msg", "Email Sudah Terdaftar di Database");
                model.addAttribute("listRole", roleService.findAll());
                model.addAttribute("user", new UserModel());
                return "user/login-v2";
            }    
        } catch (Exception e) {
            model.addAttribute("listRole", roleService.findAll());
            model.addAttribute("msg", "Email Sudah Terdaftar di Database");
            model.addAttribute("user", new UserModel());
            return "user/login-v2";
        }
        
    }

    @GetMapping("/notif/{idNotifikasi}")
    public String notifikasiLink(
            @PathVariable Long idNotifikasi,
            Model model) {
        NotifikasiModel notifikasi = notifikasiDb.findById(idNotifikasi).get();
        notifikasi.setIsNotif(false);
        notifikasiDb.save(notifikasi);

        return "redirect:" + notifikasi.getUrl();
    }

    @ModelAttribute
    public void userInformation(Principal principal, Model model) {
        try {
            UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            List<NotifikasiModel> listNotifUser = notifikasiService.getNotifListByUserAndRole(user.getIdUser(), user.getRole().getIdRole(), true);
            model.addAttribute("jumlahNotif", listNotifUser.size());
            model.addAttribute("listNotif", listNotifUser);
        } catch (Exception e) {
            model.addAttribute("jumlahNotif", null);
            model.addAttribute("listNotif", null);
        }
    }
}
