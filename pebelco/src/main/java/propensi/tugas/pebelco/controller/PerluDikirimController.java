package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/perlu-dikirim")
public class PerluDikirimController {
    @Autowired
    private UserService userService;

    @Autowired
    private PerluDikirimService perluDikirimService;

    @RequestMapping
    public String tabelPerluDikirim(Model model) {
        model.addAttribute("pengirimans", perluDikirimService.findAll());
        return "perluDikirim/tabelPerluDikirim";
    }

    @RequestMapping("/komplain/{id}")
    public String detailPengirimanKomplain(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findKomplainById(id));
        model.addAttribute("isPengiriman", false);
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdKomplain(id));
        return "pengiriman/detailPerluDikirim";
    }

    @RequestMapping("/pesanan/{id}")
    public String detailPengirimanPesanan(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findPesananById(id));
        model.addAttribute("isPengiriman", false);
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdPesanan(id));
        return "pengiriman/detailPerluDikirim";
    }

    @RequestMapping("/add/komplain/{id}")
    public String formTambahPengirimanKomplain(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findKomplainById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdKomplain(id));
        return "perluDikirim/tambahPengiriman";
    }

    @RequestMapping("/add/pesanan/{id}")
    public String formTambahPengirimanPesanan(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findPesananById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdPesanan(id));
        return "perluDikirim/tambahPengiriman";
    }

    @PostMapping("/add/komplain/")
    public String tambahPengirimanKomplainItem(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman) {

        perluDikirimService.addPengirimanKomplain(id, metodePengiriman);
        return "redirect:/pengiriman";
    }

    @PostMapping("/add/pesanan/")
    public String tambahPengirimanPesananItem(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman) {

        perluDikirimService.addPengirimanPesanan(id, metodePengiriman);
        return "redirect:/pengiriman";
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
