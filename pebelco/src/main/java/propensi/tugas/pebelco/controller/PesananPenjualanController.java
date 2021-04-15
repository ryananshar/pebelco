package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.ProdukDb;
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
    private ProdukDb produkDb;

    @GetMapping("/pesanan")
    public String listPilot(Model model) {
        List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananList();
        model.addAttribute("listPesanan", listPesanan);
        return "list-pesanan";
    }

    @RequestMapping(value="/pesanan/add", params={"addRow"})
    public String addRow(
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model,
            final BindingResult bindingResult) {
        TransaksiPesananModel barangGaib = new TransaksiPesananModel();
        List<ProdukModel> listProduk = produkDb.findAll();

        barangGaib.setPesananTransaksi(pesananPenjualan);
        pesananPenjualan.getBarangPesanan().add(barangGaib);
        
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk); 
        return "pesanan/form-add-pesanan";
    }

    @RequestMapping(value="/pesanan/add", params={"removeRow"})
    public String removeRow(
            @ModelAttribute PesananPenjualanModel pesananPenjualan, Model model, 
            final HttpServletRequest req, final BindingResult bindingResult) {
        final Integer barangId = Integer.valueOf(req.getParameter("removeRow"));
        // final TransaksiPesananModel barangPesanan = transaksiPesananService.getByIdTransaksiPesanan(barangId);
        pesananPenjualan.getBarangPesanan().remove(barangId.intValue());

        List<ProdukModel> listProduk = produkDb.findAll();
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        return "redirect:/pesanan/add";
    }

    @GetMapping("/pesanan/add")
    public String addPesananFormPage(Model model) {
        List<ProdukModel> listProduk = produkDb.findAll();
        List<TransaksiPesananModel> barangTempList = new ArrayList<TransaksiPesananModel>();
        // TransaksiPesananModel barangGoib = new TransaksiPesananModel();
        // Long kosong = (long) 0;
        // barangGoib.setHarga(kosong);
        // barangGoib.setJumlah(0);
        // barangTempList.add(barangGoib);
        PesananPenjualanModel pesananPenjualan = new PesananPenjualanModel();
        TransaksiPesananModel barangGaib = new TransaksiPesananModel();

        barangGaib.setPesananTransaksi(pesananPenjualan);
        pesananPenjualan.setBarangPesanan(barangTempList);
        pesananPenjualan.getBarangPesanan().add(barangGaib);
        // barangTempList.add(new TransaksiPesananModel());      

        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk); 
        
        return "pesanan/form-add-pesanan";
    }

    @PostMapping("/pesanan/add")
    public String addPesananSubmit(
        @ModelAttribute PesananPenjualanModel pesananPenjualan,
        Principal principal,
        Model model
    ) {
        List<ProdukModel> listProduk = produkDb.findAll();
        String email = principal.getName();
        UserModel user = userService.getUserbyEmail(email);
        Date date = new Date();
        String prefix = "PSP";
        String kode = String.valueOf(pesananPenjualan.getIdPesananPenjualan());
        List<TransaksiPesananModel> tempList = pesananPenjualan.getBarangPesanan();

        pesananPenjualan.setStatusPesanan(0);
        pesananPenjualan.setTanggalPesanan(date);
        pesananPenjualan.setIsShown(true);
        pesananPenjualan.setUser(user);
        pesananPenjualan.setKodePesananPenjualan(kode);
        pesananPenjualan.setBarangPesanan(null);

        pesananPenjualanService.addPesanan(pesananPenjualan);
        Long pesananId = pesananPenjualan.getIdPesananPenjualan();

        System.out.println("--- ini dia --- :" + pesananId);
        for (TransaksiPesananModel barang : tempList) {
            System.out.println("--- ini barang --- : " + barang.getNamaBarang());
        }
        transaksiPesananService.addAll(tempList, pesananId); 
        pesananPenjualan.setBarangPesanan(tempList);

        for (TransaksiPesananModel barang : pesananPenjualan.getBarangPesanan()) {
            System.out.println("--- ini barang id pesanan--- : " + barang.getNamaBarang());
        }
        // pesananPenjualan.getBarangPesanan()

        
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk); 

        return "pesanan/form-add-pesanan";
    }
}
