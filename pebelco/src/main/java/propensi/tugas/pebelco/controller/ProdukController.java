package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.repository.RoleDb;
import propensi.tugas.pebelco.repository.TagProdukDb;
import propensi.tugas.pebelco.service.*;

import javax.servlet.http.HttpServletRequest;


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
    private ProdukDb produkDb;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagProdukDb tagProdukDb;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping(value = "/produk")
    public String daftarproduk(Model model) {
        model.addAttribute("produk", produkService.findAll());
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk/{id}")
    public String detailproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        List<TagProdukModel> listTagProduk=produk.getListTagProduk();


        model.addAttribute("listTag", listTagProduk);
        model.addAttribute("detailProduk", produk);
        return "produk/detail-produk";
    }

    @GetMapping(value = "/produk/hapus/{id}")
    public String hapusproduk(@PathVariable Long id, Model model) {
        List<TagProdukModel> list=new ArrayList<TagProdukModel>();
        ProdukModel produk=produkService.getProdukById(id);
        tagService.deleteTagProduk(produk);
        produkService.deleteProduk(produk);

        model.addAttribute("pop", "green");
        model.addAttribute("produk", produkService.findAll());
        model.addAttribute("msg", "Produk Berhasil Dihapus");

        return "produk/daftar-produk";
    }

    @GetMapping("/produk/konfirmasi-hapus/{id}")
    public String konfirmasiHapusProduk(
            @PathVariable Long id,
            Model model) {
        model.addAttribute("pop", "konfirmasi hapus");
        model.addAttribute("msg2", "Konfirmasi Penghapusan");
        model.addAttribute("subMsg", "Apakah anda yakin ingin menghapus Produk ini?");
        model.addAttribute("produk", produkService.findAll());
        model.addAttribute("id", id);
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk/tambah")
    public String tambahProduk(Model model) {
        ProdukModel produk=new ProdukModel();
        List<TagProdukModel> tagTempList = new ArrayList<TagProdukModel>();
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        TagProdukModel tagGaib = new TagProdukModel();


        produk.setListTagProduk(tagTempList);
        produk.getListTagProduk().add(tagGaib);

        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/tambah-produk";
    }

    @PostMapping("/produk/tambah")
    public String tambahProdukSubmit(
            @ModelAttribute ProdukModel produk, Model model, @RequestParam("checkboxName") Integer[] checkboxValue){

        List<TagProdukModel> list1=new ArrayList<TagProdukModel>();
        List<ProdukModel> listproduk=new ArrayList<ProdukModel>();
        produkService.addProduk(produk);
        for(int i =0; i<checkboxValue.length;i++){
            TagProdukModel tag=new TagProdukModel();
            Long nilai=Long.valueOf(checkboxValue[i]);
            tag.setIdTagProduk(nilai);
            tag.setNamaTag(tagService.getNamaTagById(nilai));
            list1.add(tag);
            listproduk.add(produk);
            produk.setListTagProduk(list1);
            tagService.addTagProduk(listproduk);
            model.addAttribute("pop", "green");
            model.addAttribute("msg", "Produk Berhasil Ditambahkan");
        }
        model.addAttribute("produk", produk);
        return "produk/tambah-produk";
    }

    @GetMapping(value = "/produk/ubah/stok/{id}")
    public String ubahstokproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        List<Long> daftarTag=new ArrayList<>();
        List<TagProdukModel> listTagProduk=produk.getListTagProduk();

        for(int i=0; i<listTagProduk.size();i++){
            daftarTag.add(listTagProduk.get(i).getIdTagProduk());
        }
        model.addAttribute("daftarTag",daftarTag);


        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/ubah-stok-produk";
    }

    @PostMapping("/produk/ubah/stok/{id}")
    public String ubahStokProdukSubmit(
            @ModelAttribute ProdukModel produk, @PathVariable Long id, Model model){

        ProdukModel produkUpdated=produkService.updateStokProduk(produk);
        model.addAttribute("idProduk",produkUpdated.getIdProduk());
        model.addAttribute("produk", produk);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Diubah");
        return "produk/ubah-stok-produk";
    }

    @GetMapping(value = "/produk/ubah/{id}")
    public String ubahproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        List<TagProdukModel> tagTempList = new ArrayList<TagProdukModel>();
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        List<TagProdukModel> listTagProduk=produk.getListTagProduk();
        List<Long> daftarTag=new ArrayList<>();

        if (listTagProduk.size() == 0) {
            TagProdukModel tagGaib = new TagProdukModel();
            tagTempList.add(tagGaib);
            produk.setListTagProduk(tagTempList);
        }

        for(int i=0; i<listTagProduk.size();i++){
            daftarTag.add(listTagProduk.get(i).getIdTagProduk());
        }
        model.addAttribute("daftarTag",daftarTag);
        model.addAttribute("listTags", listTagProduk);
        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/ubah-produk";
    }

    @PostMapping("/produk/ubah/{id}")
    public String ubahProdukSubmit(
            @ModelAttribute ProdukModel produk, @PathVariable Long id, Model model, @RequestParam("checkboxName") Integer[] checkboxValue) {
        ProdukModel produk1=produkService.getProdukById(id);
        tagService.deleteTagProdukUpdate(produk1);
        List<TagProdukModel> list1=new ArrayList<TagProdukModel>();
        List<ProdukModel> listproduk=new ArrayList<ProdukModel>();
        produkService.addProduk(produk);
        for(int i =0; i< checkboxValue.length;i++) {
            TagProdukModel tag=new TagProdukModel();
            Long nilai=Long.valueOf(checkboxValue[i]);
            tag.setIdTagProduk(nilai);
            tag.setNamaTag(tagService.getNamaTagById(nilai));
            list1.add(tag);
            listproduk.add(produk);
            produk.setListTagProduk(list1);
            tagService.updateTagProduk(listproduk,produk1);
        }
        ProdukModel produkUpdated = produkService.updateStokProduk(produk);
        model.addAttribute("idProduk", produkUpdated.getIdProduk());
        model.addAttribute("produk", produk);
        model.addAttribute("pop", "green");
        model.addAttribute("msg", "Produk Berhasil Diubah");
        return "produk/ubah-produk";
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