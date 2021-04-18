//package propensi.tugas.pebelco.controller;
//import java.security.Principal;
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import propensi.tugas.pebelco.model.ProdukModel;
//import propensi.tugas.pebelco.model.RoleModel;
//import propensi.tugas.pebelco.model.UserModel;
//import propensi.tugas.pebelco.repository.RoleDb;
//import propensi.tugas.pebelco.service.PesananService;
//import propensi.tugas.pebelco.service.ProdukService;
//import propensi.tugas.pebelco.service.RoleService;
//import propensi.tugas.pebelco.service.UserService;
//
//@Controller
//public class PesananController {
//    @Autowired
//    private RoleDb roleDb;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProdukService produkService;
//
//    @Autowired
//    private PesananService pesananService;
//
//    @GetMapping(value = "/daftar-pesanan")
//    public String daftarpesanan(Model model) {
//        model.addAttribute("listPesanan", pesananService.findAll());
//        return "daftar-pesanan";
//    }
//
//
//
//    @ModelAttribute
//    public void userInformation(Principal principal, Model model) {
//
//        try {
//            String email = principal.getName();
//            UserModel user = userService.getUserbyEmail(email);
//            model.addAttribute("namaUser", user.getNamaPanjang());
//            model.addAttribute("roleUser", user.getRole().getNamaRole());
//        } catch (Exception e) {
//            model.addAttribute("namaUser", null);
//            model.addAttribute("roleUser", null);
//        }
//
//    }
//}
