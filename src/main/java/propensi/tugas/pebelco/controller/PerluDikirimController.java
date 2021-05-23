package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.*;
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

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private KomplainService komplainService;

    @Autowired
    private PesananPenjualanService pesananPenjualanService;

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


    @RequestMapping("/komplain/{kodeKomplain}")
    public String detailPengirimanKomplain(@PathVariable String kodeKomplain, Model model) {
        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
                if(komplain.getIsShown() && komplain.getStatusKomplain() == 1){
                    model.addAttribute("pengiriman", perluDikirimService.findKomplainByKode(kodeKomplain));
                    model.addAttribute("isPengiriman", false);
                    model.addAttribute("barangList", perluDikirimService.findAllBarangByKodeKomplain(kodeKomplain));
                }
                else{
                    model.addAttribute("message", "Data Perlu Dikirim Tidak Ditemukan");
                }

        return "pengiriman/detailPerluDikirim";
    }

    @RequestMapping("/pesanan/{kodePesanan}")
    public String detailPengirimanPesanan(@PathVariable String kodePesanan, Model model) {
        PesananPenjualanModel pesanan = pesananPenjualanService.getPesananByKodePesanan(kodePesanan);
            if (pesanan.getIsShown() && pesanan.getStatusPesanan() == 1) {
                model.addAttribute("pengiriman", perluDikirimService.findPesananByKode(kodePesanan));
                model.addAttribute("isPengiriman", false);
                model.addAttribute("barangList", perluDikirimService.findAllBarangByKodePesanan(kodePesanan));
            }
            else{
                model.addAttribute("message", "Data Perlu Dikirim Tidak Ditemukan");
            }
        return "pengiriman/detailPerluDikirim";
    }

    @RequestMapping("/tambah/komplain/{kodeKomplain}")
    public String formTambahPengirimanKomplain(@PathVariable String kodeKomplain, Model model) {
        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
            if (komplain.getIsShown() && komplain.getStatusKomplain() == 1){
                model.addAttribute("pengiriman", perluDikirimService.findKomplainByKode(kodeKomplain));
                model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
                model.addAttribute("barangList", perluDikirimService.findAllBarangByKodeKomplain(kodeKomplain));
            }
            else{
                model.addAttribute("message", "Data Perlu Dikirim Tidak Ditemukan");
            }

        return "perluDikirim/tambahPengiriman";
    }

    @RequestMapping("/tambah/pesanan/{kodePesanan}")
    public String formTambahPengirimanPesanan(@PathVariable String kodePesanan, Model model) {
        PesananPenjualanModel pesanan = pesananPenjualanService.getPesananByKodePesanan(kodePesanan);
        if (pesanan.getIsShown() && pesanan.getStatusPesanan() == 1){
            model.addAttribute("pengiriman", perluDikirimService.findPesananByKode(kodePesanan));
            model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
            model.addAttribute("barangList", perluDikirimService.findAllBarangByKodePesanan(kodePesanan));
        }
        else{
            model.addAttribute("message", "Data Perlu Dikirim Tidak Ditemukan");
        }
        return "perluDikirim/tambahPengiriman";
    }

    @PostMapping("/tambah/komplain/")
    public String tambahPengirimanKomplainItem(
            @RequestParam String kodeKomplain,
            @RequestParam Long metodePengiriman,
            Model model) {

        perluDikirimService.addPengirimanKomplain(kodeKomplain, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Ditambahkan");
        model.addAttribute("pengiriman", perluDikirimService.findKomplainByKode(kodeKomplain));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByKodeKomplain(kodeKomplain));
        return "perluDikirim/tambahPengiriman";
    }

    @PostMapping("/tambah/pesanan/")
    public String tambahPengirimanPesananItem(
            @RequestParam String kodePesanan,
            @RequestParam Long metodePengiriman,
            Model model) {

        perluDikirimService.addPengirimanPesanan(kodePesanan, metodePengiriman);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pengiriman Berhasil Ditambahkan");
        model.addAttribute("pengiriman", perluDikirimService.findPesananByKode(kodePesanan));
        model.addAttribute("metodePengiriman", perluDikirimService.findAllMetodePengiriman());
        model.addAttribute("barangList", perluDikirimService.findAllBarangByKodePesanan(kodePesanan));
        return "perluDikirim/tambahPengiriman";
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
