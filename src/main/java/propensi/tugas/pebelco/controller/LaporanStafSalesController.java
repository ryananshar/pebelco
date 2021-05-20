package propensi.tugas.pebelco.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.service.KunjunganService;
import propensi.tugas.pebelco.service.LaporanStafSalesService;
import propensi.tugas.pebelco.service.NotifikasiService;
import propensi.tugas.pebelco.service.PesananPenjualanService;
import propensi.tugas.pebelco.service.UserService;

@Controller
public class LaporanStafSalesController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private KunjunganService kunjunganService;

    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Autowired
    private LaporanStafSalesService laporanStafSalesService;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping("/laporan")
    public String halamanUtamaPengiriman(Model model) {
        return "laporan/halaman-utama-laporan";
    }

    // @RequestMapping("/")

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
