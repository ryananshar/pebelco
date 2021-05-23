package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PameranModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PameranService;
import propensi.tugas.pebelco.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class PameranController {
    @Autowired
    private UserService userService;

    @Autowired
    private NotifikasiService notifikasiService;
    
    @Autowired
    private PameranService pameranService;

    @GetMapping("/pameran")
    public String daftarPameran(Model model) {

        List<PameranModel> listPameran = pameranService.getPameranList();

        if (listPameran.isEmpty()){
            model.addAttribute("msg", "tidak ada pameran");
            model.addAttribute("pesan", "Belum Terdapat Pameran");
        }
        else{
            model.addAttribute("msg", "ada pameran");
            model.addAttribute("listPameran", listPameran);
        }

        return "pameran/daftar-pameran";
    }

    @GetMapping("/pameran/{idPameran}")
    public String detailPameran(
            @PathVariable Long idPameran,
            Model model
    ) {
        PameranModel pameran = pameranService.getPameranById(idPameran);
        model.addAttribute("pameran", pameran);

        return "pameran/detail-pameran";
    }

    @GetMapping("/pameran/tambah")
    public String tambahPameranFormPage(Model model) {
        model.addAttribute("newPameran", new PameranModel());
        return "pameran/form-tambah-pameran";
    }

    @PostMapping("/pameran/tambah")
    public String tambahPameranSubmit(
            @ModelAttribute PameranModel pameran,
            Model model) {
        if (pameran.getWaktuMulai().compareTo(pameran.getWaktuSelesai()) < 0 && pameran.getTanggalMulai().compareTo(pameran.getTanggalSelesai()) <= 0) {
            if (pameran.getCatatanPameran().equals("")) {
                pameran.setCatatanPameran(null);
            }
            pameranService.addPameran(pameran);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Pameran Berhasil Ditambahkan");
        } else {
            if (!(pameran.getTanggalMulai().compareTo(pameran.getTanggalSelesai()) <= 0)) {
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pameran Gagal Ditambahkan");
                model.addAttribute("subMsg", "Tanggal tidak valid");
            } else {
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pameran Gagal Ditambahkan");
                model.addAttribute("subMsg", "Waktu tidak valid");
            }
        }

        model.addAttribute("newPameran", pameran);
        return "pameran/form-tambah-pameran";
    }

    @GetMapping("/pameran/ubah/{idPameran}")
    public String ubahPameranFormPage(
            @PathVariable Long idPameran,
            Model model
    ) {
        PameranModel pameran = pameranService.getPameranById(idPameran);
        model.addAttribute("pameran", pameran);

        return "pameran/form-ubah-pameran";
    }

    @PostMapping("/pameran/ubah/{idPameran}")
    public String ubahPameranSubmit(
            @PathVariable Long idPameran,
            @ModelAttribute PameranModel pameran,
            Model model
    ){
        if (pameran.getWaktuMulai().compareTo(pameran.getWaktuSelesai()) < 0 && pameran.getTanggalMulai().compareTo(pameran.getTanggalSelesai()) <= 0) {
            PameranModel pameranUpdated = pameranService.updatePameran(pameran);
            model.addAttribute("pameran", pameranUpdated);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Pameran Berhasil Diubah");
        } else {
            if (!(pameran.getTanggalMulai().compareTo(pameran.getTanggalSelesai()) <= 0)) {
                model.addAttribute("pameran", pameran);
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pameran Gagal Diubah");
                model.addAttribute("subMsg", "Tanggal tidak valid");
            } else {
                model.addAttribute("pameran", pameran);
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pameran Gagal Diubah");
                model.addAttribute("subMsg", "Waktu tidak valid");
            }
        }

        return "pameran/form-ubah-pameran";
    }

    @GetMapping("/pameran/konfirmasi-hapus/{idPameran}")
    public String konfirmasiHapusPameran(
            @PathVariable Long idPameran,
            Model model) {

        List<PameranModel> listPameran = pameranService.getPameranList();

        if (listPameran.isEmpty()){
            model.addAttribute("msg", "tidak ada pameran");
            model.addAttribute("pesan", "Belum Terdapat Pameran");
        }
        else{
            model.addAttribute("msg", "ada pameran");
            model.addAttribute("listPameran", listPameran);
            model.addAttribute("pop", "konfirmasi hapus");
            model.addAttribute("msg2", "Konfirmasi Penghapusan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus pameran ini?");
            model.addAttribute("idPameran", idPameran);
        }

        return "pameran/daftar-pameran";
    }

    @GetMapping("/pameran/hapus/{idPameran}")
    public String HapusPameran(
            @PathVariable Long idPameran,
            Model model) {
        List<PameranModel> listPameran = pameranService.getPameranList();
        PameranModel pameran = pameranService.getPameranById(idPameran);

        if (listPameran.isEmpty()){
            model.addAttribute("msg", "tidak ada pameran");
            model.addAttribute("pesan", "Belum Terdapat Pameran");
        }
        else{
            model.addAttribute("msg", "ada pameran");
            model.addAttribute("listPameran", listPameran);
            pameranService.deletePameran(pameran);
            model.addAttribute("pop", "green");
            model.addAttribute("msg2", "Pameran Berhasil Dihapus");
        }

        return "pameran/daftar-pameran";
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