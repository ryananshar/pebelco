package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.KunjunganService;
import propensi.tugas.pebelco.service.LaporanStafSalesService;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PesananPenjualanService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class LaporanStafSalesController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private KunjunganService kunjunganService;

    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Autowired
    private LaporanStafSalesService laporanStafSalesService;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping("/laporan")
    public String halamanUtamaLaporan(Model model) {
        return "laporan/halaman-utama-laporan";
    }

    @RequestMapping("/laporan/staf-sales/")
    public String listLaporanStafSales(
        @RequestParam(value = "tanggalMulai", required = false) String tanggalMulai,
        @RequestParam(value = "tanggalAkhir", required = false) String tanggalAkhir,
        Model model
    ) {
        if (tanggalMulai != null && tanggalAkhir != null) {
            try {
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalMulai);
                Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAkhir);
                Map<String, Long[]> mapStafSales = new HashMap<String, Long[]>();
                List<LaporanStafSalesModel> listLaporanStaf = laporanStafSalesService.getLaporanStafSalesByDate(startDate, finalDate);
                for (LaporanStafSalesModel laporanStaf: listLaporanStaf) {
                    UserModel stafSales = laporanStaf.getStafSales();
                    if (!mapStafSales.containsKey(stafSales.getNamaPanjang())) {
                        Long[] data = new Long[] {0L, 0L, 0L};
                        if (laporanStaf.getIsKunjungan()) {
                            ++data[0];
                        } else {
                            ++data[1];
                            Integer pesananStat = laporanStaf.getPesananPenjualan().getStatusPesanan();
                            if (pesananStat == 1 || pesananStat == 3 || pesananStat == 4) {
                                data[2] += laporanStaf.getPesananPenjualan().getTotalHarga();
                            }
                        }
                        mapStafSales.put(stafSales.getNamaPanjang(), data);
                    } else {
                        Long[] data = mapStafSales.get(stafSales.getNamaPanjang());
                        if (laporanStaf.getIsKunjungan()) {
                            ++data[0];
                        } else {
                            ++data[1];
                            Integer pesananStat = laporanStaf.getPesananPenjualan().getStatusPesanan();
                            if (pesananStat == 1 || pesananStat == 3 || pesananStat == 4) {
                                data[2] += laporanStaf.getPesananPenjualan().getTotalHarga();
                            }
                        }
                        mapStafSales.put(stafSales.getNamaPanjang(), data);
                    }
                }

                if (!listLaporanStaf.isEmpty()) {
                    model.addAttribute("tanggalMulai", tanggalMulai);
                    model.addAttribute("tanggalAkhir", tanggalAkhir);
                    model.addAttribute("mapStatus", "green");
                    model.addAttribute("mapStafSales", mapStafSales);
                } else {
                    model.addAttribute("tanggalMulai", tanggalMulai);
                    model.addAttribute("tanggalAkhir", tanggalAkhir);
                    model.addAttribute("mapStatus", "red");
                    model.addAttribute("errorMsg", "Laporan Staf Sales Tidak Ditemukan");
                }
            } catch (ParseException e) {

            }
        }
        return "laporan/daftar-laporan-staf";
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
