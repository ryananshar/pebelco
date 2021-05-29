package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.repository.TagProdukDb;
import propensi.tugas.pebelco.service.*;

@Controller
public class ProdukController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagProdukDb tagProdukDb;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping(value = "/produk")
    public String daftarproduk(Model model) {

        model.addAttribute("produk", produkService.findAll());
        model.addAttribute("reverseSortDir", "asc");
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/produk-sorted")
    public String daftarProdukSort(
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {

        Page<ProdukModel> page = produkService.findPaginated(sortField, sortDir);
        List<ProdukModel> produk = page.getContent();

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("produk", produk);
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/filter-produk")
    public String daftarProdukFilter(Model model) {


        model.addAttribute("filterTitle", "Filter Produk");
        model.addAttribute("produk", produkService.findAll());
        model.addAttribute("reverseSortDir", "asc");
        model.addAttribute("pesanKey", "Tidak terdapat produk");
        return "produk/daftar-produk";
    }

    @GetMapping(value = "/search")
    public String daftarProdukBySearchbar(@Param("keyword") String keyword, Model model) {

        model.addAttribute("produk", produkService.findBySearch(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("pesanKey", "Tidak terdapat produk dengan keyword " + keyword);
        model.addAttribute("reverseSortDir", "asc");
        return "produk/daftar-produk-search";
    }

    @GetMapping(value = "/search-sorted")
    public String daftarProdukBySearchBarSort(
            @RequestParam("keyword") String keyword,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {

        List<ProdukModel> produkSearched = produkService.findBySearch(keyword);
        Page<ProdukModel> page = produkService.findPaginated(sortField, sortDir);
        List<ProdukModel> produkSorted = page.getContent();
        List<ProdukModel> produk = new ArrayList<>();

        for (int i = 0; i < produkSorted.size(); i++){
            if (produkSearched.contains(produkSorted.get(i))){
                produk.add(produkSorted.get(i));
            }
        }

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("produk", produk);
        return "produk/daftar-produk-search";
    }

    @GetMapping(value = "/search-filter")
    public String daftarProdukBySearchbarFilter(
            @RequestParam("keyword") String keyword,
            Model model)
    {
        model.addAttribute("produk", produkService.findBySearch(keyword));
        model.addAttribute("filterTitle", "Filter Produk");
        model.addAttribute("keyword", keyword);
        model.addAttribute("pesanKey", "Tidak terdapat produk dengan keyword " + keyword);
        model.addAttribute("reverseSortDir", "asc");
        return "produk/daftar-produk-search";
    }

    @GetMapping(value = "/filter")
    public String daftarProdukFiltered(
            @RequestParam("tipe") String tipe,
            @RequestParam("tag") String tags,
            @RequestParam("keyword") Optional<String> keyword,
            Model model
            ){

        List<TagProdukModel> listTagProduk = new ArrayList<>();
        List<ProdukModel> produkFiltered = new ArrayList<>();

        if (keyword.isPresent()){
            Set<ProdukModel> filtered = new HashSet<>();
            List<ProdukModel> produkSearch = produkService.findBySearch(keyword.get());
            List<ProdukModel> produkTags = new ArrayList<>();

            if(tags.equals("")){
                List<ProdukModel> produkTipe =  produkService.getProdukByTipe(Integer.parseInt(tipe));

                produkFiltered.addAll(produkTipe);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTipe.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }


                model.addAttribute("produk", filtered);
            }else if (tipe.equals("")){

                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                List<ProdukModel> produk = produkService.findAll();
                for (ProdukModel j : produk){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTags.add(j);
                    }
                }

                produkFiltered.addAll(produkTags);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTags.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }


                model.addAttribute("produk", filtered);

            }else if (!tipe.equals("") && !tags.equals("")){
                List<ProdukModel> produkTipe = produkService.getProdukByTipe(Integer.parseInt(tipe));

                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                for (ProdukModel j : produkTipe){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTags.add(j);
                    }
                }

                produkFiltered.addAll(produkTags);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTags.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }

                model.addAttribute("produk", filtered);
            }

            model.addAttribute("keyword", keyword.get());

        } else{
            if(tags.equals("")){

                model.addAttribute("produk", produkService.getProdukByTipe(Integer.parseInt(tipe)));
            }else if (tipe.equals("")){
                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                List<ProdukModel> produk = produkService.findAll();
                for (ProdukModel j : produk){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkFiltered.add(j);
                    }
                }
                model.addAttribute("produk", produkFiltered);

            }else if (!tipe.equals("") && !tags.equals("")){
                List<ProdukModel> produk = produkService.getProdukByTipe(Integer.parseInt(tipe));
                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                for (ProdukModel j : produk){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkFiltered.add(j);
                    }
                }

                model.addAttribute("produk", produkFiltered);
            }
        }
        model.addAttribute("pesanFilter", "Produk tidak ditemukan");
        model.addAttribute("reverseSortDir", "asc");
        model.addAttribute("tipe", tipe);
        model.addAttribute("tags", tags);
        return "produk/daftar-produk-filter";

    }

    @GetMapping(value = "/filter-sorted")
    public String daftarProdukByFilteredSort(
            @RequestParam("tipe") String tipe,
            @RequestParam("tag") String tags,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            @RequestParam("keyword") Optional<String> keyword,
            Model model) {
        Page<ProdukModel> page = produkService.findPaginated(sortField, sortDir);
        List<ProdukModel> produkSorted = page.getContent();

        List<TagProdukModel> listTagProduk = new ArrayList<>();
        List<ProdukModel> produkAfterSorted = new ArrayList<>();
        List<ProdukModel> produkFiltered = new ArrayList<>();

        if (keyword.isPresent()){
            Set<ProdukModel> filtered = new HashSet<>();
            List<ProdukModel> produkSearch = produkService.findBySearch(keyword.get());
            List<ProdukModel> produkTags = new ArrayList<>();

            if(tags.equals("")){
                List<ProdukModel> produkTemp = produkService.getProdukByTipe(Integer.parseInt(tipe));

                produkFiltered.addAll(produkTemp);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTemp.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }

                for (int i = 0; i < produkSorted.size(); i++){
                    if (filtered.contains(produkSorted.get(i))){
                        produkAfterSorted.add(produkSorted.get(i));
                    }
                }

                model.addAttribute("tags", tags);
                model.addAttribute("produk", produkAfterSorted);
                model.addAttribute("keyword", keyword.get());
            }else if (tipe.equals("")){
                System.out.println("masukSortNoTipekey");
                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                List<ProdukModel> produk = produkService.findAll();
                for (ProdukModel j : produk){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTags.add(j);
                    }
                }

                produkFiltered.addAll(produkTags);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTags.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }

                for (int i = 0; i < produkSorted.size(); i++){
                    if (filtered.contains(produkSorted.get(i))){
                        produkAfterSorted.add(produkSorted.get(i));
                    }
                }

                String tagsNew = "";
                for (int i = 0; i < listTags.length; i++){
                    tagsNew += listTags[i] + "+";
                }
                model.addAttribute("tags", tagsNew.substring(0,tagsNew.length()-1));
                model.addAttribute("produk", produkAfterSorted);
                model.addAttribute("keyword", keyword.get());

            }else if (!tipe.equals("") && !tags.equals("")){
                List<ProdukModel> produkTipe = produkService.getProdukByTipe(Integer.parseInt(tipe));

                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                for (ProdukModel j : produkTipe){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTags.add(j);
                    }
                }

                produkFiltered.addAll(produkTags);
                produkFiltered.addAll(produkSearch);

                for (int i = 0; i < produkFiltered.size(); i++){
                    if (produkTags.contains(produkFiltered.get(i)) && produkSearch.contains(produkFiltered.get(i))) {
                        filtered.add(produkFiltered.get(i));
                    }
                }

                for (int i = 0; i < produkSorted.size(); i++){
                    if (filtered.contains(produkSorted.get(i))){
                        produkAfterSorted.add(produkSorted.get(i));
                    }
                }

                String tagsNew = "";
                for (int i = 0; i < listTags.length; i++){
                    tagsNew += listTags[i] + "+";
                }
                model.addAttribute("tags", tagsNew.substring(0,tagsNew.length()-1));
                model.addAttribute("produk", produkAfterSorted);
                model.addAttribute("keyword", keyword.get());
            }
        }else{
            if(tags.equals("")){
                List<ProdukModel> produkTemp = produkService.getProdukByTipe(Integer.parseInt(tipe));

                for (int i = 0; i < produkSorted.size(); i++){
                    if (produkTemp.contains(produkSorted.get(i))){
                        produkFiltered.add(produkSorted.get(i));
                    }
                }
                model.addAttribute("tags", tags);
                model.addAttribute("produk", produkFiltered);
            }else if (tipe.equals("")){
                System.out.println("masukSortNoTipeNokey");
                List<ProdukModel> produkTemp = new ArrayList<>();

                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                List<ProdukModel> produkAll = produkService.findAll();
                for (ProdukModel j : produkAll){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTemp.add(j);
                    }
                }

                for (int i = 0; i < produkSorted.size(); i++){
                    if (produkTemp.contains(produkSorted.get(i))){
                        produkFiltered.add(produkSorted.get(i));
                    }
                }

                String tagsNew = "";
                for (int i = 0; i < listTags.length; i++){
                    tagsNew += listTags[i] + "+";
                }
                model.addAttribute("tags", tagsNew.substring(0,tagsNew.length()-1));
                model.addAttribute("produk", produkFiltered);

            }else if (!tipe.equals("") && !tags.equals("")){
                List<ProdukModel> produkTemp = new ArrayList<>();

                List<ProdukModel> produkTipe = produkService.getProdukByTipe(Integer.parseInt(tipe));
                String[] listTags = tags.split(" ");
                for (String i : listTags){
                    listTagProduk.add(tagService.getTagbyId(Long.parseLong(i)));
                }
                for (ProdukModel j : produkTipe){
                    if (j.getListTagProduk().containsAll(listTagProduk)){
                        produkTemp.add(j);
                    }
                }

                for (int i = 0; i < produkSorted.size(); i++){
                    if (produkTemp.contains(produkSorted.get(i))){
                        produkFiltered.add(produkSorted.get(i));
                    }
                }

                String tagsNew = "";
                for (int i = 0; i < listTags.length; i++){
                    tagsNew += listTags[i] + "+";
                }
                model.addAttribute("tags", tagsNew.substring(0,tagsNew.length()-1));
                model.addAttribute("produk", produkFiltered);
            }
        }


        model.addAttribute("tipe", tipe);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "produk/daftar-produk-filter";

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
        // List<TagProdukModel> list=new ArrayList<TagProdukModel>();
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
            Long idTag = Long.valueOf(checkboxValue[i]);
            TagProdukModel tag = tagService.getTagbyId(idTag);
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
        // System.out.println(listTagProduk);

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
        // System.out.println(checkboxValue);
        // System.out.println(checkboxValue.length);

        for(int i =0; i< checkboxValue.length;i++) {
            Long idTag = Long.valueOf(checkboxValue[i]);
            TagProdukModel tag = tagService.getTagbyId(idTag);
            list1.add(tag);
            listproduk.add(produk);
            produk.setListTagProduk(list1);
            tagService.updateTagProduk(listproduk,produk1);
            // System.out.println(tag.getNamaTag());
        }
        // System.out.println(produk.getListTagProduk());
        ProdukModel produkUpdated = produkService.updateStokProduk(produk);
        // System.out.println(produkUpdated.getListTagProduk());
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