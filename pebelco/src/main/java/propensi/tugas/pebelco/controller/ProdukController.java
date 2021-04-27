package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//        model.addAttribute("produk", produkService.findAll());
        return findPagination(1, model);
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

    @RequestMapping(value="/produk/tambah", params={"removeRow"})
    public String removeRow(
            @ModelAttribute ProdukModel produk, Model model,
            final HttpServletRequest req, final BindingResult bindingResult) {
        final Integer tagId = Integer.valueOf(req.getParameter("removeRow"));
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        produk.getListTagProduk().remove(tagId.intValue());
        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/tambah-produk";
    }

    @RequestMapping(value="/produk/tambah", params={"addRow"})
    public String addRow(
            @ModelAttribute ProdukModel produk, Model model,
            final BindingResult bindingResult) {
        List<TagProdukModel> tagTempList = produk.getListTagProduk();
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        TagProdukModel tagGaib = new TagProdukModel();

        produk.setListTagProduk(tagTempList);
        produk.getListTagProduk().add(tagGaib);

        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/tambah-produk";
    }



    @GetMapping(value = "/produk/tambah")
    public String tambahProduk(Model model) {
        ProdukModel produk=new ProdukModel();
        List<TagProdukModel> tagTempList = new ArrayList<TagProdukModel>();
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        TagProdukModel tagGaib = listTag.get(1);


        produk.setListTagProduk(tagTempList);
        produk.getListTagProduk().add(tagGaib);

        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/tambah-produk";
    }

    @PostMapping("/produk/tambah")
    public String tambahProdukSubmit(
            @ModelAttribute ProdukModel produk, Model model){

        Long harga=produk.getHarga();
        Integer stok=produk.getStok();
        if(harga<0){
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Produk Gagal Ditambahkan");
            model.addAttribute("subMsg", "Harga tidak valid");

        }
        else if (stok<0){
            model.addAttribute("pop", "red");
            model.addAttribute("msg", "Produk Gagal Ditambahkan");
            model.addAttribute("subMsg", "Stok tidak valid");
        }
        else{
            List<TagProdukModel> list2=produk.getListTagProduk();
            List<TagProdukModel> list1=new ArrayList<TagProdukModel>();
            List<ProdukModel> listproduk=new ArrayList<ProdukModel>();
            produkService.addProduk(produk);
            for(int i =0; i<list2.size();i++){
                Long z=Long.parseLong(list2.get(i).getNamaTag());
                TagProdukModel tag=new TagProdukModel();
                tag.setIdTagProduk(z);
                tag.setNamaTag(tagService.getNamaTagById(z));
                list1.add(tag);
                listproduk.add(produk);
                produk.setListTagProduk(list1);
                tagService.addTagProduk(listproduk);
            }

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

        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

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

    @RequestMapping(value="/produk/ubah/{id}", params={"removeRow"})
    public String removeRowUbah(@PathVariable Long id,
                                @ModelAttribute ProdukModel produk, Model model,
                                final HttpServletRequest req, final BindingResult bindingResult) {
        final Integer tagId = Integer.valueOf(req.getParameter("removeRow"));
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        produk.getListTagProduk().remove(tagId.intValue());

        List<TagProdukModel> listTagProduk=produk.getListTagProduk();

        model.addAttribute("listTags",listTagProduk);
        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/ubah-produk";
    }

    @RequestMapping(value="/produk/ubah/{id}", params={"addRow"})
    public String addRowUbah(@PathVariable Long id,
                             @ModelAttribute ProdukModel produk, Model model,
                             final BindingResult bindingResult) {
        List<TagProdukModel> tagTempList = produk.getListTagProduk();
        List<TagProdukModel> listTag = tagProdukDb.findAll();
        TagProdukModel tagGaib = new TagProdukModel();

        produk.setListTagProduk(tagTempList);
        produk.getListTagProduk().add(tagGaib);

        List<TagProdukModel> listTagProduk=produk.getListTagProduk();

        model.addAttribute("listTags",listTagProduk);

        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

        return "produk/ubah-produk";
    }

    @GetMapping(value = "/produk/ubah/{id}")
    public String ubahproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        List<TagProdukModel> tagTempList = new ArrayList<TagProdukModel>();
        List<TagProdukModel> listTag = tagProdukDb.findAll();

        List<TagProdukModel> listTagProduk=produk.getListTagProduk();

        if (listTagProduk.size() == 0) {
            TagProdukModel tagGaib = new TagProdukModel();
            tagTempList.add(tagGaib);
            produk.setListTagProduk(tagTempList);
        }

        model.addAttribute("listTags", produk.getListTagProduk());
        model.addAttribute("produk", produk);
        model.addAttribute("listTag", listTag);

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
            ProdukModel produk1=produkService.getProdukById(id);
            tagService.deleteTagProdukUpdate(produk1);

            List<TagProdukModel> list2=produk.getListTagProduk();
            List<TagProdukModel> list1=new ArrayList<TagProdukModel>();
            List<ProdukModel> listproduk=new ArrayList<ProdukModel>();
            produkService.addProduk(produk);
            for(int i =0; i<list2.size();i++) {
                Long z=Long.parseLong(list2.get(i).getNamaTag());
                TagProdukModel tag=new TagProdukModel();
                tag.setIdTagProduk(z);
                tag.setNamaTag(tagService.getNamaTagById(z));
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

    @GetMapping("/produk/page/{pageNo}")
    public String findPagination(
            @PathVariable (value="pageNo") int pageNo, Model model
    ){
        int pageSize = 10;

        Page<ProdukModel> page = produkService.findPaginated(pageNo, pageSize);
        List<ProdukModel> listProduk = page.getContent();

        model.addAttribute("currPage", pageNo);
        model.addAttribute("totPage", page.getTotalPages());
        model.addAttribute("totItems", page.getTotalElements());
        model.addAttribute("produk", listProduk);
        model.addAttribute("pageSize", pageSize);

        return "produk/daftar-produk";


    }
}