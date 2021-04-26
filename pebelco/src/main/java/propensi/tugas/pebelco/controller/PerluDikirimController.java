package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirim;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/perludikirim")
public class PerluDikirimController {
    @Autowired
    private UserService userService;

    @Autowired
    private PerluDikirimService perluDikirimService;

    @RequestMapping
    public String tabelPerluDikirim(Model model) {
//        model.addAttribute("pengirimans", perluDikirimService.findAll());
        List<PerluDikirim> pengirimans = perluDikirimService.findAll();
        if (pengirimans.isEmpty()){
            model.addAttribute("msg", "tidak ada pengiriman");
            model.addAttribute("pesan", "Tidak Terdapat Pesanan/Komplain yang Perlu Dilakukan Pengiriman");
        }
        else{
            model.addAttribute("msg", "ada pengiriman");
            model.addAttribute("pengirimans", pengirimans);
        }
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

    @RequestMapping("/tambah/komplain/{id}")
    public String formTambahPengirimanKomplain(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findKomplainById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdKomplain(id));
        return "perluDikirim/tambahPengiriman";
    }

    @RequestMapping("/tambah/pesanan/{id}")
    public String formTambahPengirimanPesanan(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", perluDikirimService.findPesananById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdPesanan(id));
        return "perluDikirim/tambahPengiriman";
    }

    @PostMapping("/tambah/komplain/")
    public String tambahPengirimanKomplainItem(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman,
            Model model) {

        perluDikirimService.addPengirimanKomplain(id, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Ditambahkan");
        model.addAttribute("pengiriman", perluDikirimService.findKomplainById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdKomplain(id));
        return "perluDikirim/tambahPengiriman";
    }

    @PostMapping("/tambah/pesanan/")
    public String tambahPengirimanPesananItem(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman,
            Model model) {

        perluDikirimService.addPengirimanPesanan(id, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Ditambahkan");
        model.addAttribute("pengiriman", perluDikirimService.findPesananById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByIdPesanan(id));
        return "perluDikirim/tambahPengiriman";
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
