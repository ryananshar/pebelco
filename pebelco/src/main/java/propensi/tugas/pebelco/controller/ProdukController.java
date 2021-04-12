package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.RoleModel;
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
        return "daftar-produk";
    }

    @GetMapping(value = "/produk/detail/{id}")
    public String detailproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);

        model.addAttribute("detailProduk", produk);
        return "detail-produk";
    }

    @GetMapping(value = "/produk/hapus/{id}")
    public String hapusproduk(@PathVariable Long id, Model model) {
        ProdukModel produk=produkService.getProdukById(id);
        produkService.deleteProduk(produk);

        return "delete-produk";
    }

    @ModelAttribute
    public void userInformation(Principal principal, Model model) {
        // if (principal.getName() == null) {
        //     model.addAttribute("namaUser", "null");
        //     model.addAttribute("roleUser", "null");
        // } else {
        //     String email = principal.getName();
        //     UserModel user = userService.getUserbyEmail(email);
        //     model.addAttribute("namaUser", user.getNamaPanjang());
        //     model.addAttribute("roleUser", user.getRole().getNamaRole());
        // }

        try {
            String email = principal.getName();
            UserModel user = userService.getUserbyEmail(email);
            model.addAttribute("namaUser", user.getNamaPanjang());
            model.addAttribute("roleUser", user.getRole().getNamaRole());
        } catch (Exception e) {
            model.addAttribute("namaUser", null);
            model.addAttribute("roleUser", null);
        }

        // model.addAttribute("roleUser", user.getRole());
        // return user;
    }
}
