package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.PengirimanService;
import propensi.tugas.pebelco.service.PerluDikirimService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
//@RequestMapping("/pengiriman")
public class PengirimanController {

    @Autowired
    private UserService userService;

    @Autowired
    private PerluDikirimService perluDikirimService;

    @Autowired
    private PengirimanService pengirimanService;

    @GetMapping(value = "/pengiriman")
    public String halamanUtamaPengiriman(Model model) {
        return "pengiriman/halamanUtamaPengiriman";
    }

    @RequestMapping(value = "/daftarpengiriman")
    public String tabelPengiriman(Model model) {
//        model.addAttribute("pengirimans", pengirimanService.findAll());
        List<Pengiriman> pengirimans = pengirimanService.findAll();
        if (pengirimans.isEmpty()){
            model.addAttribute("msg", "tidak ada pengiriman");
            model.addAttribute("pesan", "Tidak Terdapat Pengiriman");
        }
        else{
            model.addAttribute("msg", "ada pengiriman");
            model.addAttribute("pengirimans", pengirimans);
        }
        return "pengiriman/tabelPengiriman";
    }

    @RequestMapping("pengiriman/{id}")
    public String detailPengiriman(@PathVariable Long id, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanById(id);
        int status = pengiriman.getStatusId();
        if (pengiriman.isShown()) {
            model.addAttribute("pengiriman", pengiriman);
            model.addAttribute("isPengiriman", true);
            model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
            model.addAttribute("showUbahStatus", pengirimanService.showUbahStatusButton(status));
            model.addAttribute("showUbahPengiriman", pengirimanService.showUbahPengirimanButton(status));
        }
        else {
            model.addAttribute("message", "Data Pengiriman Tidak Ditemukan");
            model.addAttribute("pengiriman", pengiriman);
        }
            return "pengiriman/detailPengiriman";
    }



    @RequestMapping("pengiriman/update/{id}")
    public String formUbahPengiriman(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", pengirimanService.findPengirimanById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
        return "pengiriman/ubahPengiriman";
    }

    @PostMapping("pengiriman/update/")
    public String ubahPengiriman(
            @RequestParam Long id,
            @RequestParam Long metodePengiriman,
            Model model) {
        pengirimanService.updateMetodePengiriman(id, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Diubah");
        model.addAttribute("pengiriman", pengirimanService.findPengirimanById(id));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", pengirimanService.findAllBarangByIdPengiriman(id));
        return "pengiriman/ubahPengiriman";
    }

    @RequestMapping("pengiriman/update-status/{id}")
    public String formUbahStatus(@PathVariable Long id, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanById(id);
        model.addAttribute("pengiriman", pengiriman);
        model.addAttribute("status", pengiriman.getNextStatus());
        model.addAttribute("statusId", pengiriman.getNextStatusId());
        return "pengiriman/ubahStatus";
    }

    @PostMapping("pengiriman/update-status/")
    public String ubahStatus(
            @RequestParam Long id,
            @RequestParam int statusPengiriman,
            Model model) {

        // Jika sudah diterima...
        if (statusPengiriman == 3) {
            // Ke form penerimaaan barang
            return "redirect:/pengiriman/terima/" + id.toString();
        }
        else{
            pengirimanService.updateStatusPengiriman(id, statusPengiriman);
            Pengiriman pengiriman = pengirimanService.findPengirimanById(id);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Pengiriman Berhasil Diubah");
            model.addAttribute("pengiriman", pengiriman);
            model.addAttribute("status", pengiriman.getNextStatus());
            model.addAttribute("statusId", pengiriman.getNextStatusId());
            return "pengiriman/ubahStatus";
        }

    }

    @RequestMapping("pengiriman/terima/{id}")
    public String formPenerimaanBarang(@PathVariable Long id, Model model) {
        model.addAttribute("pengiriman", pengirimanService.findPengirimanById(id));
        return "pengiriman/penerimaanBarang";
    }

    @PostMapping("pengiriman/terima/")
    public String penerimaanBarang(
            @RequestParam Long id,
            @RequestParam String tanggalDiterima,
            @RequestParam String namaPenerima, Model model) throws ParseException {
        pengirimanService.updateStatusPengiriman(id, 3);
        Date tanggalDiterimaDate = new SimpleDateFormat("yyyy-mm-dd").parse(tanggalDiterima);
        pengirimanService.terimaPengiriman(id, tanggalDiterimaDate, namaPenerima);
        model.addAttribute("pengiriman", pengirimanService.findPengirimanById(id));
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Status Pengiriman Berhasil Diubah");
        return "pengiriman/penerimaanBarang";
    }

    @GetMapping("/pengiriman/konfirmasi-hapus/{id}")
    public String konfirmasiHapusPengiriman(
            @PathVariable Long id,
            Model model) {
        List<Pengiriman> pengirimans = pengirimanService.findAll();
        model.addAttribute("pengirimans", pengirimans);
        model.addAttribute("id", id);
        model.addAttribute("pop", "konfirmasi hapus");
        model.addAttribute("msg2", "Konfirmasi Penghapusan");
        model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus pengiriman ini?");

        return "pengiriman/tabelPengiriman";
    }

    @PostMapping("pengiriman/hapus/{id}")
    public String hapusPengiriman(
            @PathVariable Long id, Model model) {
        pengirimanService.setIsShownFalse(id);
        List<Pengiriman> pengirimans = pengirimanService.findAll();
        model.addAttribute("pengirimans", pengirimans);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman berhasil dihapus");
        return "pengiriman/tabelPengiriman";
    }


//        @GetMapping("/pengiriman/hapus/")
//        public String hapusPengiriman(
//                @PathVariable Long id,
//                Model model) {
//            pengirimanService.setIsShownFalse(id);
//            List<Pengiriman> pengirimans = pengirimanService.findAll();
//                    model.addAttribute("pengirimans", pengirimans);
//                    model.addAttribute("pop", "green");
//                    model.addAttribute("msg2", "Jadwal Kunjungan Berhasil Dihapus");
//
//            return "pengiriman/hapusPengiriman";
//        }

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
