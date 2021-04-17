package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pengiriman")
public class PengirimanController {

    @Autowired
    private UserService userService;

    @Autowired
    private PerluDikirimService perluDikirimService;

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("items", perluDikirimService.findAll());
        return "pengiriman/index";
    }

    @RequestMapping("/add/komplain/{id}")
    public String tambahPengirimanKomplain(@PathVariable Long id, Model model) {
        model.addAttribute("item", perluDikirimService.findKomplainById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdKomplain(id));
        return "pengiriman/tambahPengiriman";
    }

    @RequestMapping("/add/pesanan/{id}")
    public String tambahPengirimanPesanan(@PathVariable Long id, Model model) {
        model.addAttribute("item", perluDikirimService.findPesananById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdPesanan(id));
        return "pengiriman/tambahPengiriman";
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
