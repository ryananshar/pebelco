package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PengirimanService;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/pengiriman")
public class PengirimanController {

    @Autowired
    private UserService userService;

    @Autowired
    private PerluDikirimService perluDikirimService;

    @Autowired
    private PengirimanService pengirimanService;

    @RequestMapping
    public String tabelPengiriman(Model model) {
        model.addAttribute("items", pengirimanService.findAll());
        return "pengiriman/tabelPengiriman";
    }

    @RequestMapping("/{id}")
    public String detailPengiriman(@PathVariable Long id, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanById(id);
        int status = pengiriman.getStatusId();

        model.addAttribute("item", pengiriman);
        model.addAttribute("isPengiriman", true);
        model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
        model.addAttribute("showUbahStatus", pengirimanService.showUbahStatusButton(status));
        model.addAttribute("showUbahPengiriman", pengirimanService.showUbahPengirimanButton(status));
        return "pengiriman/detailPengiriman";
    }

    @RequestMapping("/update/{id}")
    public String formUbahPengiriman(@PathVariable Long id, Model model) {
        model.addAttribute("item", pengirimanService.findPengirimanById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
        return "pengiriman/ubahPengiriman";
    }

    @PostMapping("/update/")
    public String ubahPengiriman(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman) {
        pengirimanService.updateMetodePengiriman(id, metodePengiriman);
        return "redirect:/pengiriman/" + id.toString();
    }

    @RequestMapping("/update-status/{id}")
    public String formUbahStatus(@PathVariable Long id, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanById(id);
        model.addAttribute("item", pengiriman);
        model.addAttribute("status", pengiriman.getNextStatus());
        model.addAttribute("statusId", pengiriman.getNextStatusId());
        return "pengiriman/ubahStatus";
    }

    @PostMapping("/update-status/")
    public String ubahStatus(
            @RequestParam Long id,
            @RequestParam int statusPengiriman) {
        pengirimanService.updateStatusPengiriman(id, statusPengiriman);

        // Jika sudah diterima...
        if (statusPengiriman == 3) {
            // Ke form penerimaaan barang
            return "redirect:/pengiriman/terima/" + id.toString();
        }
        return "redirect:/pengiriman/" + id.toString();
    }

    @RequestMapping("/terima/{id}")
    public String formPenerimaanBarang(@PathVariable Long id, Model model) {
        model.addAttribute("item", pengirimanService.findPengirimanById(id));
        return "pengiriman/penerimaanBarang";
    }

    @PostMapping("/terima/")
    public String penerimaanBarang(
            @RequestParam Long id,
            @RequestParam String tanggalDiterima,
            @RequestParam String namaPenerima) throws ParseException {
        Date tanggalDiterimaDate = new SimpleDateFormat("yyyy-mm-dd").parse(tanggalDiterima);
        pengirimanService.terimaPengiriman(id, tanggalDiterimaDate, namaPenerima);

        return "redirect:/pengiriman/" + id.toString();
    }

    @PostMapping("/hapus/")
    public String hapusPengiriman(
            @RequestParam Long id) {
        pengirimanService.setIsShownFalse(id);
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
