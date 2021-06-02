package propensi.tugas.pebelco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.service.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class KomplainController {
    @Qualifier("komplainServiceImpl")
    @Autowired
    KomplainService komplainService;

    @Qualifier("transaksiKomplainServiceImpl")
    @Autowired
    TransaksiKomplainService transaksiKomplainService;

    @Qualifier("produkServiceImpl")
    @Autowired
    ProdukService produkService;

    @Qualifier("pesananPenjualanServiceImpl")
    @Autowired
    PesananPenjualanService pesananPenjualanService;

    @Qualifier("transaksiPesananServiceImpl")
    @Autowired
    TransaksiPesananService transaksiPesananService;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private UserService userService;

    @GetMapping("/komplain")
    private String komplainViewAll(Principal principal, Model model) {

        List<KomplainModel> komplainList;
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        if (user.getRole().getNamaRole().equals("Staf Sales") ) {
            komplainList = komplainService.getListKomplainByUser(user);
            if (komplainList.size() == 0){
                model.addAttribute("pesan", "Anda Belum Memiliki Komplain");
            } else{
                model.addAttribute("komplainList", komplainList);
            }
        } else {
            komplainList = komplainService.getListKomplain();
            if (komplainList.size() == 0){
                model.addAttribute("pesan", "Belum Terdapat Komplain");
            } else{
                model.addAttribute("komplainList", komplainList);
            }
        }

        return "komplain/komplain-viewall";
    }

    @GetMapping("/komplain/konfirmasi-hapus/{kodeKomplain}")
    private String komplainViewAllDeletePopup(
            @PathVariable String kodeKomplain,
            Model model) {

        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
        List<KomplainModel> komplainList;

        komplainList = komplainService.getListKomplain();
        if (komplainList.size() == 0){
            model.addAttribute("pesan", "Belum Terdapat Komplain");
        } else{
            model.addAttribute("komplainList", komplainList);
        }

        if (komplain.getIsShown() && (komplain.getStatusKomplain() == 0 || komplain.getStatusKomplain() == 2 || komplain.getStatusKomplain() == 3)) {
            model.addAttribute("komplainList", komplainList);
            model.addAttribute("pop", "notification");
            model.addAttribute("msg", "Konfirmasi Penghapusan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus komplain ini?");
            model.addAttribute("kodeKomplain", kodeKomplain);
        }
        // Jika Admin Komplain atau Admin menghapus komplain dengan isShown false
        else {
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
        }
        return "komplain/komplain-viewall";
    }

    @GetMapping("/komplain/hapus/{kodeKomplain}")
    private String komplainViewAllDelete(
            @PathVariable String kodeKomplain,
            Model model) {

        List<KomplainModel> komplainList;
        KomplainModel kompDelete = komplainService.getKomplainByKodeKomplain(kodeKomplain);

        komplainList = komplainService.getListKomplain();
        if (komplainList.size() == 0){
            model.addAttribute("pesan", "Belum Terdapat Komplain");
        } else{
            model.addAttribute("komplainList", komplainList);
        }

        if (kompDelete.getIsShown() && (kompDelete.getStatusKomplain() == 0 || kompDelete.getStatusKomplain() == 2 || kompDelete.getStatusKomplain() == 3)) {
            komplainService.deleteKomplain(kompDelete);

            model.addAttribute("komplainList", komplainList);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Komplain Berhasil Dihapus");
            model.addAttribute("kodeKomplain", kodeKomplain);
        }
        // Jika Admin Komplain atau Admin menghapus komplain dengan isShown false
        else {
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
        }

        return "komplain/komplain-viewall";
    }

    @GetMapping("/komplain/tambah")
    public String addKomplainPage(Model model) {

        List<PesananPenjualanModel> pesananPenjualanList;
        List<TransaksiPesananModel> transaksiPesananList;
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user.getRole().getNamaRole().equals("Staf Sales") ){
            pesananPenjualanList = pesananPenjualanService.getPesananListForStafSales( user, 3);
        } else{
            pesananPenjualanList = pesananPenjualanService.getPesananListForAdminKomplain(3);

        }

        List<List<TransaksiPesananModel>> listList = new ArrayList<>();
        List<String> listDesc = new ArrayList<>();
        List<String> listBarang = new ArrayList<>();
        List<Integer> listJumlah = new ArrayList<>();
        
        List<String> listNamaProduk = new ArrayList<>();
        List<ProdukModel> listProdukTemp = produkService.findAll();

        List<Integer> listProdukRemoval = new ArrayList<>();
        
        for (int i = 0; i < listProdukTemp.size(); i ++){
            listNamaProduk.add(listProdukTemp.get(i).getNamaProduk());
        }
        

        for (int i = 0; i < pesananPenjualanList.size(); i++) {
            transaksiPesananList = transaksiPesananService.getListByIdPesanan(pesananPenjualanList.get(i));
            if (transaksiPesananList.size() == 1){
                if (listNamaProduk.contains(transaksiPesananList.get(0).getNamaBarang())){
                    listList.add(transaksiPesananList);
                } else{
                    listProdukRemoval.add(i);
                }
            }else{
                List<TransaksiPesananModel> listTemp = new ArrayList<>();
                listTemp.addAll(transaksiPesananList);
                for (int j = 0; j < listTemp.size(); j++){
                    if (!listNamaProduk.contains(listTemp.get(j).getNamaBarang())){
                        System.out.println("yang akan dihapus " + listTemp.get(j).getNamaBarang());
                        transaksiPesananList.remove(listTemp.get(j));
                    }
                }
                listList.add(transaksiPesananList);
                
            }
        }

        if (listProdukRemoval.size() > 0){
            for (int i = 0; i < listProdukRemoval.size(); i ++){
                pesananPenjualanList.remove(pesananPenjualanList.get(listProdukRemoval.get(i)));
            }
        }

        model.addAttribute("pesananList", pesananPenjualanList);
        model.addAttribute("transaksiList", listList);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listDesc", listDesc);
        model.addAttribute("listJumlah", listJumlah);
        model.addAttribute("komplain", new KomplainModel());
        return "komplain/komplain-form-add";
    }

    @PostMapping("/komplain/tambah")
    public String addKomplainSubmit(
            @ModelAttribute KomplainModel komplain,
            String userEmail,
            Model model
    ) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<PesananPenjualanModel> pesananPenjualanList;
        pesananPenjualanList = pesananPenjualanService.getPesananList(true);
        List<TransaksiKomplainModel> transaksiKomplainList = new ArrayList<>();
        TransaksiKomplainModel transaksiKomplain;
        String[] tempTransaksi = komplain.getTemp().split("---");
        String condition = tempTransaksi[tempTransaksi.length - 1];

        if (condition.equals("aman")) {
            String indexLength = tempTransaksi[0];
            String tempBarang = tempTransaksi[1];
            String[] listTempBarang = tempBarang.split(",,,");
            String tempDesc = tempTransaksi[2];
            String[] listTempDesc = tempDesc.split(",,,");
            String tempJumlah = tempTransaksi[3];
            String[] listTempJumlah = tempJumlah.split(",,,");

            UserModel userBaru = userService.getUserbyEmail(userEmail);
            Date date = new Date();

            komplain.setIsShown(true);
            komplain.setTanggalKomplain(date);
            komplain.setUser(userBaru);
            komplain.setKodeKomplain("tes");
            komplain.setBarangKomplain(null);
            komplain.setStatusKomplain(0);


            komplainService.addKomplain(komplain);

            for (int i = 0; i < Integer.parseInt(indexLength); i++) {
                transaksiKomplain = new TransaksiKomplainModel();

                transaksiKomplain.setJumlah(Integer.parseInt(listTempJumlah[i]));
                transaksiKomplain.setNamaBarang(listTempBarang[i]);
                transaksiKomplain.setKomplainTransaksi(komplain);
                transaksiKomplain.setDeskripsiKomplain(listTempDesc[i]);

                transaksiKomplainService.addTransaksiKomplain(transaksiKomplain);
                transaksiKomplainList.add(transaksiKomplain);
            }

            komplain.setBarangKomplain(transaksiKomplainList);
            String kodeKomplain = "KOM" + komplain.getIdKomplain().toString();
            komplain.setKodeKomplain(kodeKomplain);
            komplain.setTemp(null);
            komplainService.updateKomplain(komplain);

            if (user.getRole().getNamaRole().equals("Staf Sales")){
                // setting pre-save values for notifikasi
                Boolean isNotif = true;
                String desc = "Komplain dengan id " + komplain.getKodeKomplain() + " perlu diproses";
                String url ="/komplain/" + komplain.getKodeKomplain();
                Long idPengirim = user.getIdUser();
                Long idRole = (long) 4;                 // id Sales Counter
                notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, desc, url, idPengirim, null, idRole));

            }
            
            model.addAttribute("pesananList", pesananPenjualanList);
            model.addAttribute("komplain", new KomplainModel());
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Komplain Berhasil Ditambahkan");
            model.addAttribute("subMsg", "");
        } else if (condition.equals("jumlahInvalid")) {

            model.addAttribute("pesananList", pesananPenjualanList);
            model.addAttribute("komplain", new KomplainModel());
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Komplain Gagal Ditambahkan");
            model.addAttribute("subMsg", "Jumlah tidak valid");
        } else {

            model.addAttribute("pesananList", pesananPenjualanList);
            model.addAttribute("komplain", new KomplainModel());
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Komplain Gagal Ditambahkan");
            model.addAttribute("subMsg", "Deskripsi tidak valid");
        }


        return "komplain/komplain-form-add";
    }


    @GetMapping("/komplain/{kodeKomplain}")
    public String viewDetailKomplain(
            @PathVariable(value = "kodeKomplain") String kodeKomplain,
            Model model
    ) {
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
        List<TransaksiKomplainModel> listBarangTransaksi = komplain.getBarangKomplain();
        System.out.println(komplain.getTanggalPersetujuan() + "---------------");
        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            if (komplain.getUser() == user && komplain.getIsShown()) {
                model.addAttribute("komplain", komplain);
                model.addAttribute("listBarang", listBarangTransaksi);
                model.addAttribute("user", user.getRole().getNamaRole());
            } else {
                model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            }
        } else {
            if (komplain.getIsShown()) {
                model.addAttribute("komplain", komplain);
                model.addAttribute("listBarang", listBarangTransaksi);
                model.addAttribute("user", user.getRole().getNamaRole());
            } else {
                model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            }
        }

        return "komplain/komplain-view";

    }

    @GetMapping("/komplain/ubah-status/{kodeKomplain}")
    public String viewUbahStatus(
            @PathVariable String kodeKomplain,
            Model model
    ){

        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
        if (komplain.getIsShown() && komplain.getStatusKomplain() == 0) {
            model.addAttribute("komplain", komplain);
            model.addAttribute("kodeKomplain", kodeKomplain);
        } else {
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
        }

        return "komplain/komplain-ubah-status";

    }

    @GetMapping("/komplain/konfirmasi-ubah-status/{statKomplain}/{kodeKomplain}")
    public String ubahStatusKomplainPopup(
            @PathVariable String kodeKomplain,
            @PathVariable String statKomplain,
            Model model
    ){
        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);

        if (komplain.getIsShown() == false || komplain.getStatusKomplain() != 0){
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            return "komplain/komplain-ubah-status";
        }

        model.addAttribute("komplain", komplain);
        model.addAttribute("kodeKomplain", kodeKomplain);
        if (statKomplain.equals("1")){
            model.addAttribute("pop", "statusPop");
            model.addAttribute("msg", "Konfirmasi Persetujuan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menyetujui komplain ini?");
            model.addAttribute("kodeKomplain", kodeKomplain);
        }else if (statKomplain.equals("2")){
            model.addAttribute("pop", "statusPop");
            model.addAttribute("msg", "Konfirmasi Penolakan");
            model.addAttribute("subMsg", "Apakah anda yakin ingin menolak komplain ini?");
            model.addAttribute("kodeKomplain", kodeKomplain);
        }else{
            model.addAttribute("message", "Status Komplain Tidak Dapat Diubah");
        }
        
        return "komplain/komplain-ubah-status";
    }

    @GetMapping("/komplain/ubah-status/{statKomplain}/{kodeKomplain}")
    public String ubahStatusKomplain(
            @PathVariable String kodeKomplain,
            @PathVariable String statKomplain,
            Model model
    ){
        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);

        if (komplain.getIsShown() == false || komplain.getStatusKomplain() != 0){
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            return "komplain/komplain-ubah-status";
        }

        Integer status = Integer.parseInt(statKomplain);

        if (status == 1){
            int tempStokProduk;
            int jumlahBarangDiKomplain;

            for (int i = 0; i < komplain.getBarangKomplain().size(); i++){
                ProdukModel produk = produkService.getProdukByNama(komplain.getBarangKomplain().get(i).getNamaBarang());
                tempStokProduk = produk.getStok();
                jumlahBarangDiKomplain = komplain.getBarangKomplain().get(i).getJumlah();
                if (jumlahBarangDiKomplain > tempStokProduk){
                    model.addAttribute("pop", "red");
                    model.addAttribute("msg", "Status Komplain Gagal Diubah");
                    model.addAttribute("subMsg", "Jumlah stok barang tidak cukup");
                    model.addAttribute("komplain", komplain);
                    model.addAttribute("kodeKomplain", kodeKomplain);
                    return "komplain/komplain-ubah-status";
                }
            }

            for (int i = 0; i < komplain.getBarangKomplain().size(); i++){
                ProdukModel produk = produkService.getProdukByNama(komplain.getBarangKomplain().get(i).getNamaBarang());
                tempStokProduk = produk.getStok();
                jumlahBarangDiKomplain = komplain.getBarangKomplain().get(i).getJumlah();
                produk.setStok(tempStokProduk-jumlahBarangDiKomplain);

            }

            Date date = new Date();

            komplain.setTanggalPersetujuan(date);
            komplainService.changeStatusDisetujui(komplain);

            Boolean isNotif = true;
            Long idPengirim = user.getIdUser();
            if (komplain.getUser().getRole().getIdRole() == 1) {
                String descKomplain = "Komplain dengan id " + komplain.getKodeKomplain() + " disetujui";
                String url ="/komplain/" + komplain.getKodeKomplain();
                Long idStafSales = komplain.getUser().getIdUser();
                notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descKomplain, url, idPengirim, idStafSales, null));
            }            

            String descPengiriman = "Komplain dengan id " + komplain.getKodeKomplain() + " perlu dikirim";
            String urlPengiriman ="/perludikirim/tambah/komplain/" + komplain.getKodeKomplain();
            Long idAdminPengiriman = (long) 3;
            notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descPengiriman, urlPengiriman, idPengirim, null, idAdminPengiriman));


            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Komplain Berhasil Diubah");
            model.addAttribute("subMsg", "");
            model.addAttribute("komplain", komplain);
            model.addAttribute("kodeKomplain", kodeKomplain);

        } else if (status == 2){
            komplainService.changeStatusDitolak(komplain);

            if (komplain.getUser().getRole().getIdRole() == 1) {
                Boolean isNotif = true;
                String descKomplain = "Komplain dengan id " + komplain.getKodeKomplain() + " ditolak";
                String url ="/komplain/" + komplain.getKodeKomplain();
                Long idPengirim = user.getIdUser();
                Long idStafSales = komplain.getUser().getIdUser();
                notifikasiService.addNotifikasi(new NotifikasiModel(isNotif, descKomplain, url, idPengirim, idStafSales, null));
            }

            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Status Komplain Berhasil Diubah");
            model.addAttribute("subMsg", "");
            model.addAttribute("komplain", komplain);
            model.addAttribute("kodeKomplain", kodeKomplain);

        } else{
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Status Gagal Diubah");
            model.addAttribute("subMsg", "Status tidak valid");
            model.addAttribute("komplain", komplain);
            model.addAttribute("kodeKomplain", kodeKomplain);

        }

        return "komplain/komplain-ubah-status";
    }

    @GetMapping("/komplain/ubah/{kodeKomplain}")
    public String ubahKomplain(
            @PathVariable String kodeKomplain,
            Model model
    ){

        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
        if (komplain.getIsShown() && komplain.getStatusKomplain() == 0) {
            List<String> listDesc = new ArrayList<>();
            List<String> listBarang = new ArrayList<>();
            List<Integer> listJumlah = new ArrayList<>();
            List<Integer> listId = new ArrayList<>();
            List<Integer> listMaxJumlah = new ArrayList<>();
            List<String> listCheckerPesananKomplain = new ArrayList<>();

            for (int i =0; i < komplain.getBarangKomplain().size(); i++){
                Long temp =  komplain.getBarangKomplain().get(i).getIdTransaksiKomplain();
                listId.add(temp.intValue());
                listCheckerPesananKomplain.add(komplain.getBarangKomplain().get(i).getNamaBarang());
            }


            List<TransaksiPesananModel> transaksiPesanan = komplain.getPesananKomplain().getBarangPesanan();

            for (int i=0; i < transaksiPesanan.size(); i++){
                if (listCheckerPesananKomplain.contains(transaksiPesanan.get(i).getNamaBarang())){
                    listMaxJumlah.add(transaksiPesanan.get(i).getJumlah());
                }
            }
            System.out.println(listMaxJumlah.size());


            model.addAttribute("listBarang", listBarang);
            model.addAttribute("listDesc", listDesc);
            model.addAttribute("listJumlah", listJumlah);
            model.addAttribute("listId", listId);
            model.addAttribute("listMaxJumlah", listMaxJumlah);
            model.addAttribute("komplain", komplain);
            model.addAttribute("kodeKomplain", kodeKomplain);
        } else {
            model.addAttribute("message", "Data Komplain Tidak Ditemukan");
        }

        return "komplain/komplain-form-ubah";

    }

    @PostMapping("/komplain/ubah")
    public String ubahKomplainSuccess(
            @ModelAttribute KomplainModel komplain,
            Model model
    ){
        List<TransaksiKomplainModel> transaksiKomplainList = new ArrayList<>();
        TransaksiKomplainModel transaksiKomplain;

        System.out.println(komplain.getTemp());

        KomplainModel komplainBarangDel = komplainService.getKomplainByKodeKomplain(komplain.getKodeKomplain());


        String[] tempTransaksi = komplain.getTemp().split("---");
        String condition = tempTransaksi[tempTransaksi.length - 1];

        if (condition.equals("aman")){
            String indexLength = tempTransaksi[0];
            String tempBarang = tempTransaksi[1];
            String[] listTempBarang = tempBarang.split(",,,");
            String tempDesc = tempTransaksi[2];
            String[] listTempDesc = tempDesc.split(",,,");
            String tempJumlah = tempTransaksi[3];
            String[] listTempJumlah = tempJumlah.split(",,,");
            String tempId = tempTransaksi[4];
            String[] listTempId = tempId.split(",,,");

            for (int i = 0; i < Integer.parseInt(indexLength); i++) {
                transaksiKomplain = transaksiKomplainService.getTransaksiByIdTransaksi(Long.parseLong(listTempId[i]));

                transaksiKomplain.setJumlah(Integer.parseInt(listTempJumlah[i]));
                transaksiKomplain.setNamaBarang(listTempBarang[i]);
                transaksiKomplain.setKomplainTransaksi(komplain);
                transaksiKomplain.setDeskripsiKomplain(listTempDesc[i]);

                transaksiKomplainService.updateTransaksiKomplain(transaksiKomplain);
                transaksiKomplainList.add(transaksiKomplain);
            }

            for (int i = 0; i < komplainBarangDel.getBarangKomplain().size(); i++){
                if (Arrays.stream(listTempBarang).anyMatch(komplainBarangDel.getBarangKomplain().get(i).getNamaBarang()::equals)){
                    System.out.println("sesuai");
                }else{
                    System.out.println("gak sesuai" + komplainBarangDel.getBarangKomplain().get(i).getNamaBarang());
                    transaksiKomplainService.deleteTransaksiKomplain(komplainBarangDel.getBarangKomplain().get(i));

                }
            }


            System.out.println("===============================================");

            komplain.setBarangKomplain(transaksiKomplainList);
            komplain.setTemp(null);
            komplainService.updateKomplain(komplain);

            for(int i = 0; i < komplain.getBarangKomplain().size(); i++){
                System.out.println(komplain.getBarangKomplain().get(i).getNamaBarang());
                System.out.println(komplain.getBarangKomplain().get(i).getJumlah());
                System.out.println(komplain.getBarangKomplain().get(i).getDeskripsiKomplain()  );
            }

            model.addAttribute("komplain", komplain);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Komplain Berhasil Diubah");
            model.addAttribute("subMsg", "");

        }else if (condition.equals("jumlahInvalid")) {
            model.addAttribute("komplain", komplain);
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Komplain Gagal Diubah");
            model.addAttribute("subMsg", "Jumlah tidak valid");


        }else {
            model.addAttribute("komplain", komplain);
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Komplain Gagal Diubah");
            model.addAttribute("subMsg", "Deskripsi tidak valid");
        }

        return "komplain/komplain-form-ubah";
    }

    @GetMapping("/komplain/req/{kodeKomplain}")
    public String addRequestKomplainForm(
            @PathVariable("kodeKomplain") String kodeKomplain,
            Model model
    ) {

        UserModel user = userService.getUserbyEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        KomplainModel komplain = komplainService.getKomplainByKodeKomplain(kodeKomplain);
        if (user.getRole().getNamaRole().equals("Staf Sales")) {
            if (komplain.getUser() == user && komplain.getIsShown() && komplain.getStatusKomplain() == 0) {
                model.addAttribute("komplain", komplain);
            } else {
                model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            }
        } else if (user.getRole().getNamaRole().equals("Admin") ){
            if (komplain.getIsShown()) {
                model.addAttribute("komplain", komplain);
            } else {
                model.addAttribute("message", "Data Komplain Tidak Ditemukan");
            }
        }

        return "komplain/komplain-request-change";

    }

    @PostMapping("/komplain/req/{kodePesananPenjualan}")
    public String addRequestPesananSubmit(
            @ModelAttribute KomplainModel komplain, Principal principal,
            Model model
    ) {
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        komplainService.updateKomplain(komplain);

        String descReq = "Komplain dengan id " + komplain.getKodeKomplain() + " mendapat Request Change";
        String urlPengiriman ="/komplain/" + komplain.getKodeKomplain();
        Long idAdminKomplain = (long) 4;
        notifikasiService.addNotifikasi(new NotifikasiModel(true, descReq, urlPengiriman, user.getIdUser(), null, idAdminKomplain));


        model.addAttribute("kodeKomplain", komplain.getKodeKomplain());
        model.addAttribute("komplain", komplain);
        model.addAttribute("pop", "green");
        return "komplain/komplain-request-change";
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

