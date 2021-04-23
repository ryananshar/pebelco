package propensi.tugas.pebelco.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.RoleDb;
import propensi.tugas.pebelco.service.ProdukService;
import propensi.tugas.pebelco.service.RoleService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class ProdukController {
    @Autowired
    private RoleDb roleDb;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProdukService produkService;

    @GetMapping(value = "/daftar-produk")
    public String daftarproduk(Model model) {
        model.addAttribute("listProduk", produkService.findAll());
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk/detail/{id}")
    public String detailproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);

        model.addAttribute("detailProduk", produk);
        return "produk/detail-produk";
    }

    @GetMapping(value = "/produk/hapus/{id}")
    public String hapusproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        produkService.deleteProduk(produk);

        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Dihapus");

        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk/tambah")
    public String tambahProduk(Model model) {
        ProdukModel produk=new ProdukModel();
        model.addAttribute("produk", produk);

        return "produk/tambah-produk";
    }

    @PostMapping("/produk/tambah")
    public String tambahProdukSubmit(
            @ModelAttribute ProdukModel produk, Model model){

        produkService.addProduk(produk);
        model.addAttribute("idProduk",produk.getIdProduk());
        model.addAttribute("listProduk", produk);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Ditambahkan");
        return "produk/tambah-produk";
    }

    @GetMapping(value = "/produk/ubah/stok/{id}")
    public String ubahstokproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        model.addAttribute("produk", produk);

        return "produk/ubah-produk";
    }

    @PostMapping("/produk/ubah/stok")
    public String ubahStokProdukSubmit(
            @ModelAttribute ProdukModel produk, Model model){
        ProdukModel produkUpdated=produkService.updateStokProduk(produk);
        model.addAttribute("idProduk",produkUpdated.getIdProduk());
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Diubah");
        return "produk/ubah-produk";
    }

    @GetMapping(value = "/produk/ubah/{id}")
    public String ubahproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        model.addAttribute("produk", produk);

        return "produk/ubahproduk";
    }

    @PostMapping("/produk/ubah")
    public String ubahProdukSubmit(
            @ModelAttribute ProdukModel produk, Model model){
        ProdukModel produkUpdated=produkService.updateStokProduk(produk);
        model.addAttribute("idProduk",produkUpdated.getIdProduk());
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Diubah");
        return "produk/ubahproduk";
    }

    @ModelAttribute
    public void userInformation(Principal principal, Model model) {

        try {
            String email = principal.getName();
            UserModel user = userService.getUserbyEmail(email);
            model.addAttribute("namaUser", user.getNamaPanjang());
            model.addAttribute("roleUser", user.getRole().getNamaRole());
        } catch (Exception e) {
            model.addAttribute("namaUser", null);
            model.addAttribute("roleUser", null);
        }
        
    }
}
