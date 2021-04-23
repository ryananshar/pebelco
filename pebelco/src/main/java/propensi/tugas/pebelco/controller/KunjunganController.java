package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.KunjunganService;
import propensi.tugas.pebelco.service.UserService;

import java.util.List;

@Controller
public class KunjunganController {
    @Autowired
    KunjunganService kunjunganService;

    @Autowired
    UserService userService;

    // @GetMapping("/")
    // private String home(){
    //     return "home";
    // }

    @GetMapping("/kunjungan")
    public String daftarKunjungan(Model model) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            List<KunjunganModel> listKunjunganStafSales = kunjunganService.getKunjunganListByStafSalesByIsShown(user, true);

            if (listKunjunganStafSales.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("message", "Anda Belum Memiliki Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjunganStafSales);
            }
        }

        else {
            List<KunjunganModel> listKunjungan = kunjunganService.getKunjunganListByIsShown(true);

            if (listKunjungan.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("message", "Belum Terdapat Daftar Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjungan);
            }
        }

        return "kunjungan/daftar-kunjungan";
    }

    @GetMapping("/kunjungan/{kodeKunjungan}")
    public String detailKunjungan(
            @PathVariable String kodeKunjungan,
            Model model
    ) {
        KunjunganModel kunjungan = kunjunganService.getKunjunganByKodeKunjungan(kodeKunjungan);
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown() == true) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Staf Sales membuka halaman detail kunjungan yang bukan miliknya atau yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }
        else {
            if (kunjungan.getIsShown() == true) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Admin membuka halaman detail kunjungan yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

        return "kunjungan/detail-kunjungan";
    }

    @GetMapping("/kunjungan/tambah")
    public String tambahKunjunganFormPage(Model model) {
        model.addAttribute("newKunjungan", new KunjunganModel());
        return "kunjungan/form-tambah-kunjungan";
    }

    @PostMapping("/kunjungan/tambah")
    public String tambahKunjunganSubmit(
            @ModelAttribute KunjunganModel kunjungan,
            Model model) {
        UserModel stafSales = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (kunjungan.getWaktuMulai().compareTo(kunjungan.getWaktuSelesai()) < 0) {
            kunjungan.setStafSales(stafSales);
            kunjungan.setIsShown(true);
            if (kunjungan.getCatatanKunjungan().equals("")) {
                kunjungan.setCatatanKunjungan(null);
            }
            kunjunganService.addKunjungan(kunjungan);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Jadwal Kunjungan Berhasil Ditambahkan");
        } else {
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Jadwal Kunjungan Gagal Ditambahkan");
            model.addAttribute("subMsg", "Waktu tidak valid");
        }

        model.addAttribute("newKunjungan", new KunjunganModel());
        return "kunjungan/form-tambah-kunjungan";
    }

    @GetMapping("/kunjungan/ubah/{kodeKunjungan}")
    public String ubahKunjunganFormPage(
            @PathVariable String kodeKunjungan,
            Model model
    ) {
        KunjunganModel kunjungan = kunjunganService.getKunjunganByKodeKunjungan(kodeKunjungan);
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown() == true) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Staf Sales membuka halaman ubah kunjungan yang bukan miliknya atau yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }
        else {
            if (kunjungan.getIsShown() == true) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Admin membuka halaman ubah kunjungan yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

//        model.addAttribute("kunjungan", kunjungan);
        return "kunjungan/form-ubah-kunjungan";
    }

    @PostMapping("/kunjungan/ubah/{kodeKunjungan}")
    public String ubahKunjunganSubmit(
            @PathVariable String kodeKunjungan,
            @ModelAttribute KunjunganModel kunjungan,
            Model model
    ){
        if (kunjungan.getWaktuMulai().compareTo(kunjungan.getWaktuSelesai()) < 0) {
            KunjunganModel kunjunganUpdated = kunjunganService.updateKunjungan(kunjungan);
            model.addAttribute("kunjungan", kunjunganUpdated);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Jadwal Kunjungan Berhasil Diubah");
        } else {
            model.addAttribute("kunjungan", kunjungan);
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Jadwal Kunjungan Gagal Diubah");
            model.addAttribute("subMsg", "Waktu tidak valid");
        }

//        model.addAttribute("kunjungan", kunjunganUpdated);
        return "kunjungan/form-ubah-kunjungan";
    }

    // hapus kunjungan belum kelar, blm sesuai sama ucs
    @GetMapping("/kunjungan/hapus/{kodeKunjungan}")
    public String hapusKunjungan(
            @PathVariable String kodeKunjungan,
            Model model
    ) {
        KunjunganModel kunjungan = kunjunganService.getKunjunganByKodeKunjungan(kodeKunjungan);
        model.addAttribute("kunjungan", kunjungan);
        kunjunganService.deleteKunjungan(kunjungan);
        return "kunjungan/hapus-kunjungan";
    }
}
