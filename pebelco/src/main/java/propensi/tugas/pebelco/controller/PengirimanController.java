package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.PengirimanDb;
import propensi.tugas.pebelco.service.NotifikasiService;
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

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private PengirimanDb pengirimanDb;

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
            model.addAttribute("pesan", "Belum Terdapat Pengiriman");
        }
        else{
            model.addAttribute("msg", "ada pengiriman");
            model.addAttribute("pengirimans", pengirimans);
        }
        return "pengiriman/tabelPengiriman";
    }

    @RequestMapping("pengiriman/{kodePengiriman}")
    public String detailPengiriman(@PathVariable String kodePengiriman, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);
        int status = pengiriman.getStatusId();
        if (pengiriman.isShown()) {
            model.addAttribute("pengiriman", pengiriman);
            model.addAttribute("isPengiriman", true);
            model.addAttribute("barangList", pengirimanService.findAllBarangByKodePengiriman(kodePengiriman));
            model.addAttribute("showUbahStatus", pengirimanService.showUbahStatusButton(status));
            model.addAttribute("showUbahPengiriman", pengirimanService.showUbahPengirimanButton(status));
        }
        else {
            model.addAttribute("message", "Data Pengiriman Tidak Ditemukan");
            model.addAttribute("pengiriman", pengiriman);
        }
            return "pengiriman/detailPengiriman";
    }



    @RequestMapping("pengiriman/ubah/{kodePengiriman}")
    public String formUbahPengiriman(@PathVariable String kodePengiriman, Model model) {
        model.addAttribute("pengiriman", pengirimanService.findPengirimanByKode(kodePengiriman));
        model.addAttribute("metode", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", pengirimanService.findAllBarangByKodePengiriman(kodePengiriman));
        return "pengiriman/ubahPengiriman";
    }

    @PostMapping("pengiriman/ubah/")
    public String ubahPengiriman(
            @RequestParam String kodePengiriman,
            @RequestParam Long metodePengiriman,
            Model model) {
        pengirimanService.updateMetodePengiriman(kodePengiriman, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Diubah");
        model.addAttribute("pengiriman", pengirimanService.findPengirimanByKode(kodePengiriman));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", pengirimanService.findAllBarangByKodePengiriman(kodePengiriman));
        return "pengiriman/ubahPengiriman";
    }

    @RequestMapping("pengiriman/ubah-status/{kodePengiriman}")
    public String formUbahStatus(@PathVariable String kodePengiriman, Model model) {
        Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);
        model.addAttribute("pengiriman", pengiriman);
        model.addAttribute("status", pengiriman.getNextStatus());
        model.addAttribute("statusId", pengiriman.getNextStatusId());
        return "pengiriman/ubahStatus";
    }

    @PostMapping("pengiriman/ubah-status/")
    public String ubahStatus(
            @RequestParam String kodePengiriman,
            @RequestParam int statusPengiriman,
            Model model) {

        // Jika sudah diterima...
        if (statusPengiriman == 3) {
            // Ke form penerimaaan barang
            return "redirect:/pengiriman/terima/" + kodePengiriman;
        }
        else{
            UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            pengirimanService.updateStatusPengiriman(kodePengiriman, statusPengiriman);
            Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);

            Boolean isNotif = true;
            String desc;
            Long idStafSales = (long) 0;
            if (pengiriman.getKode().contains("KOM")) {
                idStafSales = pengirimanDb.findByKodePengiriman(kodePengiriman).getKomplain().getUser().getIdUser();
                desc = "Komplain dengan id " + pengiriman.getKode() + " telah " + pengiriman.getStatus() + " pada " + pengiriman.getKodePengiriman();
            } else {
                idStafSales = pengirimanDb.findByKodePengiriman(kodePengiriman).getPesananPenjualan().getUser().getIdUser();
                desc = "Pesanan dengan id " + pengiriman.getKode() + " telah " + pengiriman.getStatus() + " pada " + pengiriman.getKodePengiriman();
            }
            String url ="/pengiriman/" + pengiriman.getKodePengiriman();
            Long idPengirim = user.getIdUser();
            notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, desc, url, idPengirim, idStafSales, null));

            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Pengiriman Berhasil Diubah");
            model.addAttribute("pengiriman", pengiriman);
            model.addAttribute("status", pengiriman.getNextStatus());
            model.addAttribute("statusId", pengiriman.getNextStatusId());
            return "pengiriman/ubahStatus";
        }

    }

    @RequestMapping("pengiriman/terima/{kodePengiriman}")
    public String formPenerimaanBarang(@PathVariable String kodePengiriman, Model model) {
        model.addAttribute("pengiriman", pengirimanService.findPengirimanByKode(kodePengiriman));
        return "pengiriman/penerimaanBarang";
    }

    @PostMapping("pengiriman/terima/")
    public String penerimaanBarang(
            @RequestParam String kodePengiriman,
            @RequestParam String tanggalDiterima,
            @RequestParam String namaPenerima, Model model) throws ParseException {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        pengirimanService.updateStatusPengiriman(kodePengiriman, 3);
        Date tanggalDiterimaDate = new SimpleDateFormat("yyyy-mm-dd").parse(tanggalDiterima);
        pengirimanService.terimaPengiriman(kodePengiriman, tanggalDiterimaDate, namaPenerima);

        Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);
        Boolean isNotif = true;
        String desc;
        Long idStafSales = (long) 0;
        if (pengiriman.getKode().contains("KOM")) {
            idStafSales = pengirimanDb.findByKodePengiriman(kodePengiriman).getKomplain().getUser().getIdUser();
            desc = "Komplain dengan id " + pengiriman.getKode() + " telah " + pengiriman.getStatus() + " pada " + pengiriman.getKodePengiriman();
        } else {
            idStafSales = pengirimanDb.findByKodePengiriman(kodePengiriman).getPesananPenjualan().getUser().getIdUser();
            desc = "Pesanan dengan id " + pengiriman.getKode() + " telah " + pengiriman.getStatus() + " pada " + pengiriman.getKodePengiriman();
        }
        String url ="/pengiriman/" + pengiriman.getKodePengiriman();
        Long idPengirim = user.getIdUser();
        notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, desc, url, idPengirim, idStafSales, null));

        model.addAttribute("pengiriman", pengirimanService.findPengirimanByKode(kodePengiriman));
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Status Pengiriman Berhasil Diubah");
        return "pengiriman/penerimaanBarang";
    }

    @GetMapping("/pengiriman/konfirmasi-hapus/{kodePengiriman}")
    public String konfirmasiHapusPengiriman(
            @PathVariable String kodePengiriman,
            Model model) {
        List<Pengiriman> pengirimans = pengirimanService.findAll();
        if (pengirimans.isEmpty()){
            model.addAttribute("msg", "tidak ada pengiriman");
            model.addAttribute("pesan", "Belum Terdapat Pengiriman");
        }
        else{
            model.addAttribute("msg", "ada pengiriman");
            model.addAttribute("pengirimans", pengirimans);
        }
        model.addAttribute("pengirimans", pengirimans);
        model.addAttribute("kodePengiriman", kodePengiriman);
        model.addAttribute("pop", "konfirmasi hapus");
        model.addAttribute("msg2", "Konfirmasi Penghapusan");
        model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus pengiriman ini?");

        return "pengiriman/tabelPengiriman";
    }

    @PostMapping("pengiriman/hapus/{kodePengiriman}")
    public String hapusPengiriman(
            @PathVariable String kodePengiriman, Model model) {
        List<Pengiriman> pengirimans = pengirimanService.findAll();
        if (pengirimans.isEmpty()){
            model.addAttribute("msg", "tidak ada pengiriman");
            model.addAttribute("pesan", "Belum Terdapat Pengiriman");
        }
        else{
            model.addAttribute("msg", "ada pengiriman");
            model.addAttribute("pengirimans", pengirimans);
        }
        pengirimanService.setIsShownFalse(kodePengiriman);
        model.addAttribute("pengirimans", pengirimans);
        model.addAttribute("pop", "green");
        model.addAttribute("msg2", "Pengiriman berhasil dihapus");
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
