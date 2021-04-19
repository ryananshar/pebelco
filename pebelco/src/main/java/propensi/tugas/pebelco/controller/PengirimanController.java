package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PengirimanService;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/pengiriman")
public class PengirimanController {

    @Autowired
    private UserService userService;

    @Autowired
    private PengirimanService pengirimanService;

    @RequestMapping
    public String tabelPengiriman(Model model) {
        model.addAttribute("items", pengirimanService.findAll());
        return "pengiriman/tabelPengiriman";
    }

    @RequestMapping("/{id}")
    public String detailPengiriman(@PathVariable Long id, Model model) {
        model.addAttribute("item", pengirimanService.findPengirimanById(id));
        model.addAttribute("isPengiriman", true);
        model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
        return "pengiriman/detailPengiriman";
    }

    @ModelAttribute
    public void userInformation(Principal principal, Model model) {
        try {
            String email = principal.getName();
            UserModel user = userService.getUserbyEmail(email);
            model.addAttribute("namaUser", user.getNamaPanjang());
            model.addAttribute("roleUser", user.getRole().getNamaRole());
        } catch (Exception e) {
            model.addAttribute("namaUser", null);
            model.addAttribute("roleUser", null);
        }
    }
}
