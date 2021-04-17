package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listPesanan(Model model) {
        List<PesananPenjualanModel> listPesanan = pesananPenjualanService.getPesananList();
        model.addAttribute("listPesanan", listPesanan);
        return "pesanan/list-pesanan";
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
        return "pesanan/form-add-pesanan";
    }

    @GetMapping("/pesanan/add")
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
        List<TransaksiPesananModel> tempList = pesananPenjualan.getBarangPesanan();

        pesananPenjualan.setStatusPesanan(0);
        pesananPenjualan.setTanggalPesanan(date);
        pesananPenjualan.setIsShown(true);
        pesananPenjualan.setUser(user);
        pesananPenjualan.setKodePesananPenjualan("belum");
        pesananPenjualan.setBarangPesanan(null);

        pesananPenjualanService.addPesanan(pesananPenjualan);
        Long pesananId = pesananPenjualan.getIdPesananPenjualan();
        transaksiPesananService.addAll(tempList, pesananId); 
        
        String prefix = "PSP";
        String kode = String.valueOf(pesananPenjualan.getIdPesananPenjualan());
        Integer diskon = pesananPenjualan.getDiskon();
        Long hargaTotal = pesananPenjualanService.calculateTotal(tempList, diskon);
        pesananPenjualan.setKodePesananPenjualan(prefix+kode);
        pesananPenjualan.setBarangPesanan(tempList);
        pesananPenjualan.setTotalHarga(hargaTotal);
        pesananPenjualanService.updatePesanan(pesananPenjualan);
                
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("listProduk", listProduk);
        model.addAttribute("pop", "green"); 

        return "pesanan/form-add-pesanan";
    }

    @GetMapping("/pesanan/view/{kodePesananPenjualan}")
    public String viewDetailPesanan(
        @PathVariable(value = "kodePesananPenjualan") String kodePesananPenjualan,
        Model model
    ) {
        try {
            PesananPenjualanModel pesananPenjualan = pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
            model.addAttribute("pesananPenjualan", pesananPenjualan);
            List<TransaksiPesananModel> listbarang = pesananPenjualan.getBarangPesanan();  
            model.addAttribute("listbarang", listbarang);           

            return "pesanan/detail-pesanan";
        } catch (NullPointerException e) {
            String message = "Proses Pencarian Gagal Karena ID Pesanan Tidak Ditemukan";
            model.addAttribute("message", message);
            return "pesanan/detail-pesanan";
        }
                
    }

    @GetMapping("/pesanan/req/{kodePesananPenjualan}")
    public String addRequestPesananForm(
        @PathVariable("kodePesananPenjualan") String kodePesananPenjualan,
        Model model
    ) {
        PesananPenjualanModel pesananPenjualan = pesananPenjualanService.getPesananByKodePesanan(kodePesananPenjualan);
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        return "pesanan/request-change";
    }

    @PostMapping("/pesanan/req/{kodePesananPenjualan}")
    public String addRequestPesananSubmit(
        @ModelAttribute PesananPenjualanModel pesananPenjualan,
        Model model
    ) {
        pesananPenjualanService.updatePesanan(pesananPenjualan);
        model.addAttribute("kodePesananPenjualan", pesananPenjualan.getKodePesananPenjualan());
        model.addAttribute("pesananPenjualan", pesananPenjualan);
        model.addAttribute("pop", "green"); 
        return "pesanan/request-change";     
    }
    // tester
}
