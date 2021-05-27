package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PengirimanService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;

import java.util.ArrayList;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class LaporanPengirimanController {

    @Autowired
    private UserService userService;

    @Autowired
    private PengirimanService pengirimanService;

    @Autowired
    private NotifikasiService notifikasiService;

    @RequestMapping(value = "/laporan/laporan-pengiriman/")
    public String daftarLaporanPengiriman(
        @RequestParam(value = "tanggalMulai", required = false) String tanggalMulai,
        @RequestParam(value = "tanggalAkhir", required = false) String tanggalAkhir,
        Model model) {

        if (tanggalMulai != null && tanggalAkhir != null) {
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalMulai);
                Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAkhir);
                List<Pengiriman> allPengiriman = pengirimanService.getPengirimanByDate(startDate, finalDate);
                List<Pengiriman> pengirimans = new ArrayList<Pengiriman>();
                int jumlahHasan = 0;
                int jumlahBK = 0;
                int jumlahAran = 0;
                int jumlahGlobal = 0;

                for (Pengiriman a: allPengiriman) {
                    if (a.getStatusId() == 3) {
                        pengirimans.add(a);
                        if (a.getMetodePengiriman().equals("H. Hasan")){
                            jumlahHasan++;
                        } else if (a.getMetodePengiriman().equals("Berkat Kawan")){
                            jumlahBK++;
                        } else if (a.getMetodePengiriman().equals("Aran")){
                            jumlahAran++;
                        }else if (a.getMetodePengiriman().equals("Global")){
                            jumlahGlobal++;
                        }
                    }
                }
                model.addAttribute("jumlahHasan", jumlahHasan);
                model.addAttribute("jumlahBK", jumlahBK);
                model.addAttribute("jumlahAran", jumlahAran);
                model.addAttribute("jumlahGlobal", jumlahGlobal);

                model.addAttribute("tanggalMulai", tanggalMulai);
                model.addAttribute("tanggalAkhir", tanggalAkhir);
                if (pengirimans.isEmpty()){
                    model.addAttribute("mapStatus", "red");
                    model.addAttribute("errorMsg", "Laporan Pengiriman Tidak Ditemukan");
                }
                else{

                    model.addAttribute("mapStatus", "green");
                    model.addAttribute("msg", "ada pengiriman");
                    model.addAttribute("pengirimans", pengirimans);
                }
            }catch (ParseException e) {

            }
        } return "laporan/laporanPengiriman";
    }

    @RequestMapping("/laporan/laporan-pengiriman/{tanggalMulai}/{tanggalAkhir}/{kodePengiriman}")
    public String detailLaporanPengiriman(@PathVariable String kodePengiriman,
                                          @PathVariable(value = "tanggalMulai", required = false) String tanggalMulai,
                                          @PathVariable(value = "tanggalAkhir", required = false) String tanggalAkhir,
                                          Model model) {
        try {
            Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);
            if (pengiriman.getStatusId() == 3) {
                model.addAttribute("pengiriman", pengiriman);
                model.addAttribute("isPengiriman", true);
                model.addAttribute("barangList", pengirimanService.findAllBarangByKodePengiriman(kodePengiriman));
            } else {
                model.addAttribute("message", "Data Pengiriman Tidak Ditemukan");
                model.addAttribute("pengiriman", pengiriman);
            }
        } catch (NullPointerException e) {
            model.addAttribute("message", "Data Pengiriman Tidak Ditemukan");
        }
        
        model.addAttribute("tanggalMulai", tanggalMulai);
        model.addAttribute("tanggalAkhir", tanggalAkhir);
        return "laporan/detailLaporanPengiriman";
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