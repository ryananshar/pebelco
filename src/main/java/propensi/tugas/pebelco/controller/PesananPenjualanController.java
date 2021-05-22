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

import propensi.tugas.pebelco.repository.PesananPenjualanDb;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.repository.TransaksiPesananDb;
import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.LaporanStafSalesService;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PesananPenjualanService;
import propensi.tugas.pebelco.service.TransaksiPesananService;
import propensi.tugas.pebelco.service.UserService;
import propensi.tugas.pebelco.service.ProdukService;

@Controller
public class PesananPenjualanController {
    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Autowired
    private PesananPenjualanDb pesananPenjualanDb;

    @Autowired
    private TransaksiPesananDb transaksiPesananDb;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransaksiPesananService transaksiPesananService;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private ProdukDb produkDb;

    @Autowired
    private LaporanStafSalesService laporanStafSalesService;

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
        }
        else {
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

        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        Date date = new Date();
        List<TransaksiPesananModel> tempList = pesananPenjualan.getBarangPesanan();
        List<TransaksiPesananModel> checkList = pesananPenjualan.getBarangPesanan();

        for (TransaksiPesananModel barang : tempList) {
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

        // add laporan staf sales
        laporanStafSalesService.addLaporanStafSales(new LaporanStafSalesModel(user, false, date, pesananPenjualan));

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
            List<TransaksiPesananModel> listbarang = pesananPenjualan.getBarangPesanan();
            if (user.getRole().getNamaRole().equals("Staf Sales")) {
                if (pesananPenjualan.getUser() == user && pesananPenjualan.getIsShown()) {
                    model.addAttribute("pesananPenjualan", pesananPenjualan);
                    model.addAttribute("listbarang", listbarang);
                } else {
                    model.addAttribute("message", "Data Pesanan Penjualan Tidak Ditemukan");
                }
            }
            else {
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

    @RequestMapping(value="/pesanan/ubah/{kodePesananPenjualan}", params={"addRowUbah"})
    public String addRowUbah(@PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model,
            final BindingResult bindingResult) {
        TransaksiPesananModel barangGaib = new TransaksiPesananModel();
        List<ProdukModel> listProduk = produkDb.findAll();
        List<TransaksiPesananModel> barangExist = pesananPenjualan.getBarangPesanan();

        barangGaib.setPesananTransaksi(pesananPenjualan);
        barangExist.add(barangGaib);
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "pesanan/ubah-pesanan";
    }

    @RequestMapping(value="/pesanan/ubah/{kodePesananPenjualan}", params={"removeRowUbah"})
    public String removeRowUbah(@PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model,
            final HttpServletRequest req, final BindingResult bindingResult) {
        final Integer barangId = Integer.valueOf(req.getParameter("removeRowUbah"));
        // final TransaksiPesananModel barangPesanan = transaksiPesananService.getByIdTransaksiPesanan(barangId);
        pesananPenjualan.getBarangPesanan().remove(barangId.intValue());

        List<ProdukModel> listProduk = produkDb.findAll();
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "pesanan/ubah-pesanan";
    }


    @GetMapping("pesanan/ubah/{kodePesananPenjualan}")
    public String ubahPesanan(@PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
                              Model model){
        PesananPenjualanModel pesananPenjualan=pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        List<ProdukModel> listProduk=produkDb.findAll();

        model.addAttribute("listProduk", listProduk);
        model.addAttribute("pesananPenjualan",pesananPenjualan);
        return "pesanan/ubah-pesanan";

    }

    @PostMapping("/pesanan/ubah/{kodePesananPenjualan}")
    public String ubahPesananSubmit(
            @ModelAttribute PesananPenjualanModel pesananPenjualan,
            @PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
            Principal principal, final BindingResult bindingResult,
            Model model
    ) {
        List<ProdukModel> listProduk = produkDb.findAll();
        Integer diskon = pesananPenjualan.getDiskon();
        if (diskon == null) {
            diskon = 0;
        }
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        Date date = new Date();
        List<TransaksiPesananModel> tempList = pesananPenjualan.getBarangPesanan();
        List<TransaksiPesananModel> checkList = pesananPenjualan.getBarangPesanan();

        for (TransaksiPesananModel barang : tempList) {
            // Handle duplicate
            if (checkList.stream().filter(o -> o.getNamaBarang().equals(barang.getNamaBarang())).skip(1).findAny().isPresent()) {
                model.addAttribute("pesananPenjualan", pesananPenjualan);
                model.addAttribute("listProduk", listProduk);
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pesanan Penjualan Gagal Diubah");
                model.addAttribute("subMsg", "Nama barang tidak dapat berulang");

                return "pesanan/ubah-pesanan";
            } else if (barang.getJumlah() <= 0) {
                model.addAttribute("pesananPenjualan", pesananPenjualan);
                model.addAttribute("listProduk", listProduk);
                model.addAttribute("pop", "red");
                model.addAttribute("msg", "Pesanan Penjualan Gagal Diubah");
                model.addAttribute("subMsg", "Jumlah Barang tidak valid");

                return "pesanan/ubah-pesanan";
            }
        }

        Long idPesanan=pesananPenjualan.getIdPesananPenjualan();
        // delete transaksi pesanan yang ada di tabel transaksipesanan
        PesananPenjualanModel pesanan1=pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        List<TransaksiPesananModel>barang=transaksiPesananService.getListByIdPesanan(pesanan1);
        for(int k=0;k<barang.size();k++){
            transaksiPesananService.deleteTransaksiPesanan(barang.get(k).getIdTransaksiPesanan());
        }

        pesananPenjualanService.updatePesanan(pesanan1);
        transaksiPesananService.addAll(pesanan1.getBarangPesanan(),idPesanan);
        List<TransaksiPesananModel>transaksi=transaksiPesananService.getListByIdPesananPenjualan(idPesanan);
        for(int j=0;j<transaksi.size();j++){
            transaksiPesananService.deleteTransaksiPesanan(transaksi.get(j).getIdTransaksiPesanan());
        }

        pesananPenjualan.setStatusPesanan(0);
        pesananPenjualan.setTanggalPesanan(date);
        pesananPenjualan.setIsShown(true);
        pesananPenjualan.setUser(user);
        pesananPenjualan.setKodePesananPenjualan(kodePesananPenjualan);
        pesananPenjualan.setBarangPesanan(null);

        pesananPenjualanService.addPesanan(pesanan1);
        transaksiPesananService.addAll(tempList, idPesanan);

        Long hargaTotal = pesananPenjualanService.calculateTotal(tempList, diskon);
        pesananPenjualan.setIdPesananPenjualan(pesananPenjualan.getIdPesananPenjualan());
        pesananPenjualan.setAlamatToko(pesananPenjualan.getAlamatToko());
        pesananPenjualan.setBarangPesanan(pesananPenjualan.getBarangPesanan());
        pesananPenjualan.setDiskon(pesananPenjualan.getDiskon());
        pesananPenjualan.setNamaToko(pesananPenjualan.getNamaToko());
        pesananPenjualan.setKodePesananPenjualan(kodePesananPenjualan);
        pesananPenjualan.setBarangPesanan(tempList);
        pesananPenjualan.setStatusPesanan(0);
        pesananPenjualan.setUser(user);
        pesananPenjualan.setTotalHarga(hargaTotal);


        pesananPenjualanService.updatePesanan(pesananPenjualan);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Pesanan Penjualan Berhasil Diubah");
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "pesanan/ubah-pesanan";
    }

    @GetMapping("pesanan/ubah-status/{kodePesananPenjualan}")
    public String ubahStatusPesanan(@PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
                              Model model){
        PesananPenjualanModel pesananPenjualan=pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        Integer status=pesananPenjualan.getStatusPesanan();

        model.addAttribute("pesanan", pesananPenjualan);
        model.addAttribute("statPesanan",status);
        return "pesanan/ubah-status-pesanan";
    }

    @GetMapping("/pesanan/ubah-status/{statPesanan}/{kodePesananPenjualan}")
    public String ubahStatusPesananSubmit(
            @PathVariable(value = "kodePesananPenjualan") String kodePesanan,
            @PathVariable String statPesanan,
            Model model
    ){
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        PesananPenjualanModel pesanan=pesananPenjualanService.getPesananByKodePesanan(kodePesanan);
        if (pesanan.getIsShown() == false || pesanan.getStatusPesanan() != 0){
            model.addAttribute("message", "Data Pesanan Tidak Ditemukan");
            return "pesanan/ubah-status-pesanan";
        }
        Integer status = Integer.parseInt(statPesanan);
        if (status == 1){
            Date date = new Date();
            pesanan.setTanggalPersetujuan(date);
            pesananPenjualanService.changeStatusDisetujui(pesanan);
            Boolean isNotif = true;
            String descPesanan = "Pesanan dengan id " + pesanan.getKodePesananPenjualan() + " disetujui";
            String url ="/pesanan/" + pesanan.getKodePesananPenjualan();
            Long idPengirim = user.getIdUser();
            Long idStafSales = pesanan.getUser().getIdUser();
            notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descPesanan, url, idPengirim, idStafSales, null));

            String descPengiriman = "Pesanan dengan id " + pesanan.getKodePesananPenjualan() + " perlu dikirim";
            String urlPengiriman ="/perludikirim/tambah/pesanan/" + pesanan.getKodePesananPenjualan();
            Long idAdminPengiriman = (long) 3;
            notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descPengiriman, urlPengiriman, idPengirim, null, idAdminPengiriman));

            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Pesanan Berhasil Diubah");
            model.addAttribute("subMsg", "");
            model.addAttribute("pesanan", pesanan);
            model.addAttribute("kodePesanan", kodePesanan);

        } else if (status == 2){
            pesananPenjualanService.changeStatusDitolak(pesanan);

            Boolean isNotif = true;
            String descPesanan = "Pesanan dengan id " + pesanan.getKodePesananPenjualan() + " ditolak";
            String url ="/pesanan/" + pesanan.getKodePesananPenjualan();
            Long idPengirim = user.getIdUser();
            Long idStafSales = pesanan.getUser().getIdUser();
            notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descPesanan, url, idPengirim, idStafSales, null));

            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Pesanan Berhasil Diubah");
            model.addAttribute("subMsg", "");
            model.addAttribute("pesanan", pesanan);
            model.addAttribute("kodePesanan", kodePesanan);

        } else{
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Status Gagal Diubah");
            model.addAttribute("subMsg", "Status tidak valid");
            model.addAttribute("pesanan", pesanan);
            model.addAttribute("kodePesanan", kodePesanan);

        }

        return "pesanan/ubah-status-pesanan";
    }

    @GetMapping("/pesanan/konfirmasi-ubah-status/{statPesanan}/{kodePesanan}")
    public String ubahStatusPesananPopup(
            @PathVariable String kodePesanan,
            @PathVariable String statPesanan,
            Model model
    ){
        PesananPenjualanModel pesanan=pesananPenjualanService.getPesananByKodePesanan(kodePesanan);

        if (pesanan.getIsShown() == false || pesanan.getStatusPesanan() != 0){
            model.addAttribute("message", "Data Pesanan Tidak Ditemukan");
            return "pesanan/ubah-status-pesanan";
        }

        model.addAttribute("pesanan", pesanan);
        model.addAttribute("kodePesanan", kodePesanan);
        if (statPesanan.equals("1")){
            model.addAttribute("pop", "statusPop");
            model.addAttribute("msg", "Konfirmasi Persetujuan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menyetujui pesanan ini?");
            model.addAttribute("kodePesanan", kodePesanan);
        }else if (statPesanan.equals("2")){
            model.addAttribute("pop", "statusPop");
            model.addAttribute("msg", "Konfirmasi Penolakan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menolak pesanan ini?");
            model.addAttribute("kodePesanan", kodePesanan);
        }else{
            model.addAttribute("message", "Status Pesanan Tidak Dapat Diubah");
        }
        return "pesanan/ubah-status-pesanan";
    }

    @GetMapping("/pesanan/konfirmasi-hapus/{kodePesananPenjualan}")
    public String konfirmasiHapusPesanan(
            @PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
            Model model
    ) {
        List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananList(true);
        model.addAttribute("pop", "konfirmasi hapus");
        model.addAttribute("msg2", "Konfirmasi Penghapusan");
        model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus pesanan ini?");
        model.addAttribute("listPesanan", listPesanan);
        model.addAttribute("id", kodePesananPenjualan);

        return "pesanan/list-pesanan";
    }

    @GetMapping(value = "/pesanan/hapus/{kodePesananPenjualan}")
    public String hapusPesanan(@PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan, Model model,Principal principal) {
        PesananPenjualanModel pesanan=pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananListByUser(user, true);

        pesanan.setIsShown(false);

        PesananPenjualanModel pesananPenjualan = pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);

        model.addAttribute("pop", "green");
        model.addAttribute("pesanan", pesananPenjualan);
        model.addAttribute("listPesanan", listPesanan);
        model.addAttribute("msg", "Pesanan Penjualan Berhasil Dihapus");

        return "pesanan/list-pesanan";
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