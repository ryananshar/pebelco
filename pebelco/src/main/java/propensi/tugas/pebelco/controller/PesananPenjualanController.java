package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PesananPenjualanService;
import propensi.tugas.pebelco.service.TransaksiPesananService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class PesananPenjualanController {
    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransaksiPesananService transaksiPesananService;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private ProdukDb produkDb;

    @GetMapping("/pesanan")
    public String listPesanan(Principal principal, Model model) {
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        if (user.getRole().getNamaRole().equals("Staf Sales") ) {
            List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananListByUser(user, true);
            if (listPesanan.isEmpty()){
                model.addAttribute("msg", "error");
                model.addAttribute("message", "Anda Belum Memiliki Pesanan Penjualan");
            }
            else{
                model.addAttribute("listPesanan", listPesanan);
            }
        } else {
            List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananList(true);
            if (listPesanan.isEmpty()){
                model.addAttribute("msg", "error");
                model.addAttribute("message", "Belum Terdapat Pesanan Penjualan");
            }
            else{
                model.addAttribute("listPesanan", listPesanan);
            }
        }

        return "pesanan/list-pesanan";
    }

    @RequestMapping(value="/pesanan/tambah", params={"addRow"})
    public String addRow(
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model,
            final BindingResult bindingResult) {
        TransaksiPesananModel barangGaib = new TransaksiPesananModel();
        List<ProdukModel> listProduk = produkDb.findAll();
        List<TransaksiPesananModel> barangExist = pesananPenjualan.getBarangPesanan();

        barangGaib.setPesananTransaksi(pesananPenjualan);
        barangExist.add(barangGaib);
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "pesanan/form-add-pesanan";
    }

    @RequestMapping(value="/pesanan/tambah", params={"removeRow"})
    public String removeRow(
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model,
            final HttpServletRequest req, final BindingResult bindingResult) {
        System.out.println(req.getParameter("removeRow"));
        final Integer barangId = Integer.valueOf(req.getParameter("removeRow"));
        // final TransaksiPesananModel barangPesanan = transaksiPesananService.getByIdTransaksiPesanan(barangId);
        pesananPenjualan.getBarangPesanan().remove(barangId.intValue());

        List<ProdukModel> listProduk = produkDb.findAll();
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "pesanan/form-add-pesanan";
    }

    @GetMapping("/pesanan/tambah")
    public String addPesananFormPage(Model model) {
        List<ProdukModel> listProduk = produkDb.findAll();
        List<TransaksiPesananModel> barangTempList = new ArrayList<TransaksiPesananModel>();
        PesananPenjualanModel pesananPenjualan = new PesananPenjualanModel();
        TransaksiPesananModel barangGaib = new TransaksiPesananModel();

        // add at least one object
        pesananPenjualan.setBarangPesanan(barangTempList);
        pesananPenjualan.getBarangPesanan().add(barangGaib);

        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);

        return "pesanan/form-add-pesanan";
    }

    @PostMapping("/pesanan/tambah")
    public String addPesananSubmit(
        @ModelAttribute PesananPenjualanModel pesananPenjualan,
        Principal principal, final BindingResult bindingResult,
        Model model
    ) {
        List<ProdukModel> listProduk = produkDb.findAll();
        Integer diskon = pesananPenjualan.getDiskon();

        if (diskon == null) {
            diskon = 0;
        }

        if (diskon >= 0 && diskon <= 100) {
            String email = principal.getName();
            UserModel user = userService.getUserbyEmail(email);
            Date date = new Date();
            List<TransaksiPesananModel> tempList = pesananPenjualan.getBarangPesanan();
            List<TransaksiPesananModel> checkList = pesananPenjualan.getBarangPesanan();

            for (TransaksiPesananModel barang : tempList) {
                Integer stokProduk = produkDb.findByNamaProduk(barang.getNamaBarang()).getStok();
                // Handle duplicate
                if (checkList.stream().filter(o -> o.getNamaBarang().equals(barang.getNamaBarang())).skip(1).findAny().isPresent()) {
                    model.addAttribute("pesananPenjualan", pesananPenjualan);
                    model.addAttribute("listProduk", listProduk);
                    model.addAttribute("pop", "red");
                    model.addAttribute("msg", "Pesanan Penjualan Gagal Ditambahkan");
                    model.addAttribute("subMsg", "Nama barang tidak dapat berulang");

                    return "pesanan/form-add-pesanan";
                } else if (barang.getJumlah() <= 0) {
                    model.addAttribute("pesananPenjualan", pesananPenjualan);
                    model.addAttribute("listProduk", listProduk);
                    model.addAttribute("pop", "red");
                    model.addAttribute("msg", "Pesanan Penjualan Gagal Ditambahkan");
                    model.addAttribute("subMsg", "Jumlah Barang tidak valid");

                    return "pesanan/form-add-pesanan";
                } 
                // else if (barang.getJumlah() > stokProduk) {
                //     model.addAttribute("pesananPenjualan", pesananPenjualan);
                //     model.addAttribute("listProduk", listProduk);
                //     model.addAttribute("pop", "red");
                //     model.addAttribute("msg", "Pesanan Penjualan Gagal Ditambahkan");
                //     model.addAttribute("subMsg", "Jumlah Barang melebihi stok");

                //     return "pesanan/form-add-pesanan";
                // }
            }

            // initiate pesanan penjualan early value
            pesananPenjualan.setStatusPesanan(0);
            pesananPenjualan.setTanggalPesanan(date);
            pesananPenjualan.setIsShown(true);
            pesananPenjualan.setUser(user);
            pesananPenjualan.setKodePesananPenjualan("belum");
            pesananPenjualan.setBarangPesanan(null);

            // save pesanan penjualan and all transaksi pesanan to repository
            pesananPenjualanService.addPesanan(pesananPenjualan);
            Long pesananId = pesananPenjualan.getIdPesananPenjualan();
            transaksiPesananService.addAll(tempList, pesananId);

            // setting remaining values for pesanan penjualan
            String prefix = "PSP";
            String kode = String.valueOf(pesananPenjualan.getIdPesananPenjualan());
            Long hargaTotal = pesananPenjualanService.calculateTotal(tempList, diskon);
            pesananPenjualan.setKodePesananPenjualan(prefix+kode);
            pesananPenjualan.setBarangPesanan(tempList);
            pesananPenjualan.setTotalHarga(hargaTotal);
            pesananPenjualanService.updatePesanan(pesananPenjualan);

            if (user.getRole().getNamaRole().equals("Staf Sales")) {
                // setting pre-save values for notifikasi
                Boolean isNotif = true;
                String desc = "Pesanan Penjualan dengan id " + pesananPenjualan.getKodePesananPenjualan() + " perlu diproses";
                String url ="/pesanan/" + pesananPenjualan.getKodePesananPenjualan();
                Long idPengirim = user.getIdUser();
                Long idRole = (long) 2;                 // id Sales Counter
                notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, desc, url, idPengirim, null, idRole));
            }
           

            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Pesanan Penjualan Berhasil Ditambahkan");
        } else {
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Pesanan Penjualan Gagal Ditambahkan");
            model.addAttribute("subMsg", "Diskon tidak valid");
        }
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);

        return "pesanan/form-add-pesanan";
    }

    @GetMapping("/pesanan/{kodePesananPenjualan}")
    public String viewDetailPesanan(
            @PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
            Model model
    ) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            PesananPenjualanModel pesananPenjualan = pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
            System.out.println(pesananPenjualan.getUser().getRole().getNamaRole());
            List<TransaksiPesananModel> listbarang = pesananPenjualan.getBarangPesanan();
            if (user.getRole().getNamaRole().equals("Staf Sales")) {
                if (pesananPenjualan.getUser() == user && pesananPenjualan.getIsShown()) {
                    model.addAttribute("pesananPenjualan", pesananPenjualan);
                    model.addAttribute("listbarang", listbarang);
                } else {
                    model.addAttribute("message", "Data Pesanan Penjualan Tidak Ditemukan");
                }
            } else {
                if (pesananPenjualan.getIsShown()) {
                    model.addAttribute("pesananPenjualan", pesananPenjualan);
                    model.addAttribute("listbarang", listbarang);
                } else {
                    model.addAttribute("message", "Data Pesanan Penjualan Tidak Ditemukan");
                }
            }

            return "pesanan/detail-pesanan";

    }

    @GetMapping("/pesanan/req/{kodePesananPenjualan}")
    public String addRequestPesananForm(
            @PathVariable("kodePesananPenjualan") String kodePesananPenjualan,
            Model model
    ) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        PesananPenjualanModel pesananPenjualan = pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            if (pesananPenjualan.getUser() == user && pesananPenjualan.getIsShown() && pesananPenjualan.getStatusPesanan() == 0) {
                model.addAttribute("pesananPenjualan", pesananPenjualan);
            } else {
                model.addAttribute("message", "Data Pesanan Penjualan Tidak Ditemukan");
            }
        } else {
            if (pesananPenjualan.getIsShown()) {
                model.addAttribute("pesananPenjualan", pesananPenjualan);
            } else {
                model.addAttribute("message", "Data Pesanan Penjualan Tidak Ditemukan");
            }
        }
        return "pesanan/request-change";
    }

    @PostMapping("/pesanan/req/{kodePesananPenjualan}")
    public String addRequestPesananSubmit(
        @ModelAttribute PesananPenjualanModel pesananPenjualan, Principal principal,
        Model model
    ) {
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        pesananPenjualanService.updatePesanan(pesananPenjualan);

        // setting pre-save values for notifikasi
        Boolean isNotif = true;
        String desc = "Pesanan Penjualan dengan id " + pesananPenjualan.getKodePesananPenjualan() + " mendapat Request Change";
        String url ="/pesanan/" + pesananPenjualan.getKodePesananPenjualan();
        Long idPengirim = user.getIdUser();
        Long idRole = (long) 2;                 // id Sales Counter
        notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, desc, url, idPengirim, null, idRole));

        model.addAttribute("kodePesananPenjualan", pesananPenjualan.getKodePesananPenjualan());
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("pop", "green");
        return "pesanan/request-change";
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
