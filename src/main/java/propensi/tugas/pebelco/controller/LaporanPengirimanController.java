package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PengirimanService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;

import java.util.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

                for (Pengiriman a : allPengiriman) {
                    if (a.getStatusId() == 3) {
                        pengirimans.add(a);
                        if (a.getMetodePengiriman().equals("H. Hasan")) {
                            jumlahHasan++;
                        } else if (a.getMetodePengiriman().equals("Berkat Kawan")) {
                            jumlahBK++;
                        } else if (a.getMetodePengiriman().equals("Aran")) {
                            jumlahAran++;
                        } else if (a.getMetodePengiriman().equals("Global")) {
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
                if (pengirimans.isEmpty()) {
                    model.addAttribute("mapStatus", "red");
                    model.addAttribute("errorMsg", "Laporan Pengiriman Tidak Ditemukan");
                } else {

                    model.addAttribute("mapStatus", "green");
                    model.addAttribute("msg", "ada pengiriman");
                    model.addAttribute("pengirimans", pengirimans);
                }
            } catch (ParseException e) {

            }
        } else {
            model.addAttribute("mainPage", "belum search");
        }
        return "laporan/laporanPengiriman";
    }

    @RequestMapping("/laporan/laporan-pengiriman/{tanggalMulai}/{tanggalAkhir}/{kodePengiriman}")
    public String detailLaporanPengiriman(@PathVariable String kodePengiriman,
                                          @PathVariable(value = "tanggalMulai", required = false) String tanggalMulai,
                                          @PathVariable(value = "tanggalAkhir", required = false) String tanggalAkhir,
                                          Model model) throws ParseException {

        try {
            Pengiriman pengiriman = pengirimanService.findPengirimanByKode(kodePengiriman);
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalMulai);
            Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAkhir);
            if (pengiriman.getStatusId() == 3) {
                if (startDate.compareTo(pengiriman.getTanggalDiterima()) <= 0 && pengiriman.getTanggalDiterima().compareTo(finalDate) <= 0) {
                    model.addAttribute("pengiriman", pengiriman);
                    model.addAttribute("isPengiriman", true);
                    model.addAttribute("barangList", pengirimanService.findAllBarangByKodePengiriman(kodePengiriman));
                } else {
                    model.addAttribute("message", "Data Laporan Pengiriman Tidak Ditemukan");
                    model.addAttribute("pengiriman", pengiriman);
                }
            } else {
                model.addAttribute("message", "Data Laporan Pengiriman Tidak Ditemukan");
                model.addAttribute("pengiriman", pengiriman);
            }
        } catch (NullPointerException e) {
            model.addAttribute("message", "Data Laporan Pengiriman Tidak Ditemukan");
        }

        model.addAttribute("tanggalMulai", tanggalMulai);
        model.addAttribute("tanggalAkhir", tanggalAkhir);

        return "laporan/detailLaporanPengiriman";
    }



//    @GetMapping(value = "/laporan/laporan-pengiriman/{tanggalMulai}/{tanggalAkhir}/filter")
//    public String daftarLaporanFilter(Model model) {
//
//        model.addAttribute("filterTitle", "Filter Pengiriman");
//        model.addAttribute("pengiriman", pengirimanService.findAll());
//        model.addAttribute("reverseSortDir", "asc");
//        model.addAttribute("pesanKey", "Tidak terdapat pengiriman");
//        return "produk/daftar-produk";
//    }
//
//    @GetMapping(value = "/filter")
//    public String daftarLaporanFiltered(
//            @RequestParam("keyword") Optional<String> keyword,
//            Model model
//    ){
//        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalMulai);
//        Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(tanggalAkhir);
//        List<Pengiriman> allPengiriman = pengirimanService.getPengirimanByDate(startDate, finalDate);
//        List<Pengiriman> pengirimans = new ArrayList<Pengiriman>();
//
//        if (keyword.isPresent()){
//            Set<PengirimanModel> filtered = new HashSet<>();
//
//            if(metode.equals("")){
//                List<PengirimanModel> metode =  pengirimanService.getPengirimanByMetode();
//
//                for (int i = 0; i < pengirimanFiltered.size(); i++){
//                    if (metode.contains(pengirimanFiltered.get())) {
//                        filtered.add(pengirimanFiltered.get());
//                    }
//                }
//
//
//        } else{
//            if(metode.equals("")){
//
//                model.addAttribute("pengiriman", pengirimanService.getPengirimanByMetode());
//
//                for (ProdukModel i : metode){
//                    if (j.getMetodePengiriman().containsAll(metode)){
//                        pengirimanFiltered.add(j);
//                    }
//                }
//                model.addAttribute("produk", pengirimanFiltered);
//
//            }else if (!metode.equals("")){
//                List<PengirimanModel> pengiriman = pengirimanService.getPengirimanByMetode();
//                for (PengirimanModel j : pengiriman){
//                    if (j.getListTagProduk().containsAll(listTagProduk)){
//                        produkFiltered.add(j);
//                    }
//                }
//
//                model.addAttribute("produk", produkFiltered);
//            }
//        }
//        model.addAttribute("pesanFilter", "Produk tidak ditemukan");
//        model.addAttribute("reverseSortDir", "asc");
//        model.addAttribute("tipe", tipe);
//        model.addAttribute("tags", tags);
//        return "laporan/daftar-produk-filter";
//
//    }



}