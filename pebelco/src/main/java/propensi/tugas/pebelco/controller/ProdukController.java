package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.RoleDb;
import propensi.tugas.pebelco.service.*;

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

    @Autowired
    private TagService tagService;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping(value = "/produk")
    public String daftarproduk(Model model) {
        model.addAttribute("listProduk", produkService.findAll());
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk/{id}")
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
        List<TagProdukModel> produkTempList = new ArrayList<TagProdukModel>();
        TagProdukModel tagGaib = new TagProdukModel();

        produk.setListTagProduk(produkTempList);
        produk.getListTagProduk().add(tagGaib);

        model.addAttribute("produk", produk);
        model.addAttribute("tag", tagService.findAll());

        return "produk/tambah-produk";
    }

    @PostMapping("/produk/tambah")
    public String tambahProdukSubmit(
            @ModelAttribute ProdukModel produk, Principal principal, Model model){

        Long harga=produk.getHarga();
        Integer stok=produk.getStok();
        if(harga<0){
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Produk Gagal Ditambahkan");
            model.addAttribute("subMsg", "Harga tidak valid");

//            return "produk/tambah-produk";
        }
        else if (stok<0){
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Produk Gagal Ditambahkan");
            model.addAttribute("subMsg", "Stok tidak valid");

//            return "produk/tambah-produk";
        }
        else{
//            List<TagProdukModel> tempList = produk.getListTagProduk();
//
//            Long idproduk=produk.getIdProduk();
//            tagService.addAll(tempList,idproduk);
//            produkService.addProduk(produk);

            produkService.addProduk(produk);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Produk Berhasil Ditambahkan");
        }
        model.addAttribute("produk", produk);
        return "produk/tambah-produk";
    }

    @GetMapping(value = "/produk/ubah/stok/{id}")
    public String ubahstokproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        model.addAttribute("produk", produk);

        return "produk/ubah-stok-produk";
    }

    @PostMapping("/produk/ubah/stok/{id}")
    public String ubahStokProdukSubmit(
            @ModelAttribute ProdukModel produk, @PathVariable Long id, Model model){
        Integer stok=produk.getStok();

        if(stok<0){
            model.addAttribute("pop", "red");
            model.addAttribute("produk", produk);
            model.addAttribute("msg", "Produk Gagal Diubah");
            model.addAttribute("subMsg", "Stok tidak valid");

            return "produk/ubah-stok-produk";

        }else{
            ProdukModel produkUpdated=produkService.updateStokProduk(produk);
            model.addAttribute("idProduk",produkUpdated.getIdProduk());
            model.addAttribute("produk", produk);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Produk Berhasil Diubah");
            return "produk/ubah-stok-produk";
        }

    }

    @GetMapping(value = "/produk/ubah/{id}")
    public String ubahproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        model.addAttribute("produk", produk);

        return "produk/ubah-produk";
    }

    @PostMapping("/produk/ubah/{id}")
    public String ubahProdukSubmit(
            @ModelAttribute ProdukModel produk, @PathVariable Long id, Model model) {
        Long harga = produk.getHarga();

        if (harga < 0) {
            model.addAttribute("pop", "red");
            model.addAttribute("produk", produk);
            model.addAttribute("msg", "Produk Gagal Diubah");
            model.addAttribute("subMsg", "Harga tidak valid");
            return "produk/ubah-produk";
        }
        else {
            ProdukModel produkUpdated = produkService.updateStokProduk(produk);
            model.addAttribute("idProduk", produkUpdated.getIdProduk());
            model.addAttribute("produk", produk);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Produk Berhasil Diubah");
            return "produk/ubah-produk";
        }

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
