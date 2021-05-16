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
import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.KunjunganService;
import propensi.tugas.pebelco.service.LaporanStafSalesService;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class KunjunganController {
    @Autowired
    private KunjunganService kunjunganService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired 
    private LaporanStafSalesService laporanStafSalesService;

    @GetMapping("/kunjungan")
    public String daftarKunjungan(Model model) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            List<KunjunganModel> listKunjunganStafSales = kunjunganService.getKunjunganListByStafSalesByIsShown(user, true);

            if (listKunjunganStafSales.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("pesan", "Anda Belum Memiliki Jadwal Kunjungan");
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
                model.addAttribute("pesan", "Belum Terdapat Jadwal Kunjungan");
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
            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown()) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Staf Sales membuka halaman detail kunjungan yang bukan miliknya atau yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }
        else {
            if (kunjungan.getIsShown()) {
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
            laporanStafSalesService.addLaporanStafSales(new LaporanStafSalesModel(stafSales, true, kunjungan.getTanggalKunjungan(), kunjungan));
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Jadwal Kunjungan Berhasil Ditambahkan");
        } else {
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Jadwal Kunjungan Gagal Ditambahkan");
            model.addAttribute("subMsg", "Waktu tidak valid");
        }

        model.addAttribute("newKunjungan", kunjungan);
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
            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown()) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Staf Sales membuka halaman ubah kunjungan yang bukan miliknya atau yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }
        else {
            if (kunjungan.getIsShown()) {
                model.addAttribute("kunjungan", kunjungan);
            }
            // Jika Admin membuka halaman ubah kunjungan yang sudah dihapus
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

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

        return "kunjungan/form-ubah-kunjungan";
    }

    @GetMapping("/kunjungan/konfirmasi-hapus/{kodeKunjungan}")
    public String konfirmasiHapusKunjungan(
            @PathVariable String kodeKunjungan,
            Model model) {
        KunjunganModel kunjungan = kunjunganService.getKunjunganByKodeKunjungan(kodeKunjungan);
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            List<KunjunganModel> listKunjunganStafSales = kunjunganService.getKunjunganListByStafSalesByIsShown(user, true);

            if (listKunjunganStafSales.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("pesan", "Anda Belum Memiliki Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjunganStafSales);
            }

            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown()) {
                model.addAttribute("listKunjungan", listKunjunganStafSales);
                model.addAttribute("pop", "konfirmasi hapus");
                model.addAttribute("msg2", "Konfirmasi Penghapusan");
                model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus jadwal kunjungan ini?");
                model.addAttribute("kodeKunjungan", kodeKunjungan);
            }
            // Jika Staf Sales menghapus kunjungan yang bukan miliknya atau kunjungan dengan isShown false
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

        else {
            List<KunjunganModel> listKunjungan = kunjunganService.getKunjunganListByIsShown(true);

            if (listKunjungan.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("pesan", "Belum Terdapat Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjungan);
            }

            if (kunjungan.getIsShown()) {
                model.addAttribute("listKunjungan", listKunjungan);
                model.addAttribute("pop", "konfirmasi hapus");
                model.addAttribute("msg2", "Konfirmasi Penghapusan");
                model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus jadwal kunjungan ini?");
                model.addAttribute("kodeKunjungan", kodeKunjungan);
            }
            // Jika Admin menghapus kunjungan dengan isShown false
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

        return "kunjungan/daftar-kunjungan";
    }

    @GetMapping("/kunjungan/hapus/{kodeKunjungan}")
    public String HapusKunjungan(
            @PathVariable String kodeKunjungan,
            Model model) {
        KunjunganModel kunjungan = kunjunganService.getKunjunganByKodeKunjungan(kodeKunjungan);
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            List<KunjunganModel> listKunjunganStafSales = kunjunganService.getKunjunganListByStafSalesByIsShown(user, true);

            if (listKunjunganStafSales.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("pesan", "Anda Belum Memiliki Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjunganStafSales);
            }

            if (kunjungan.getStafSales().getIdUser() == user.getIdUser() && kunjungan.getIsShown()) {
                kunjunganService.deleteKunjungan(kunjungan);
                model.addAttribute("listKunjungan", listKunjunganStafSales);
                model.addAttribute("pop", "green");
                model.addAttribute("msg2", "Jadwal Kunjungan Berhasil Dihapus");
            }
            // Jika Staf Sales menghapus kunjungan yang bukan miliknya atau kunjungan dengan isShown false
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

        else {
            List<KunjunganModel> listKunjungan = kunjunganService.getKunjunganListByIsShown(true);

            if (listKunjungan.isEmpty()){
                model.addAttribute("msg", "tidak ada kunjungan");
                model.addAttribute("pesan", "Belum Terdapat Jadwal Kunjungan");
            }
            else{
                model.addAttribute("msg", "ada kunjungan");
                model.addAttribute("listKunjungan", listKunjungan);
            }

            if (kunjungan.getIsShown()) {
                kunjunganService.deleteKunjungan(kunjungan);
                model.addAttribute("listKunjungan", listKunjungan);
                model.addAttribute("pop", "green");
                model.addAttribute("msg2", "Jadwal Kunjungan Berhasil Dihapus");
            }
            // Jika Admin menghapus kunjungan dengan isShown false
            else {
                model.addAttribute("message", "Data Jadwal Kunjungan Tidak Ditemukan");
            }
        }

        return "kunjungan/daftar-kunjungan";
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