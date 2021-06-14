package propensi.tugas.pebelco.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;

public interface PesananPenjualanService {
    //  Method untuk menambah objek
    void addPesanan(PesananPenjualanModel pesananPenjualan);

    //  Method untuk mendapatkan list data yang telah tersimpan
    List<PesananPenjualanModel> getPesananList(Boolean bolean);

    //  Method untuk mendapatkan list data yang telah tersimpan
    List<PesananPenjualanModel> getPesananListByUser(UserModel user, Boolean bolean);

    //  Method untuk mendapatkan data berdasarkan id
    PesananPenjualanModel getPesananByIdPesanan(Long idPesananPenjualan);

    //  Method untuk mendapatkan data berdasarkan id
    PesananPenjualanModel getPesananByKodePesanan(String kodePesananPenjualan);

    // Method untuk update 
    PesananPenjualanModel updatePesanan(PesananPenjualanModel pesananPenjualan);

    // Method untuk delete 
    void deletePesanan(PesananPenjualanModel pesananPenjualan) throws Exception;

    Long calculateTotal(List<TransaksiPesananModel> barangPesanan, Integer diskon);

    void changeStatusDisetujui(PesananPenjualanModel pesanan);

    void changeStatusDitolak(PesananPenjualanModel pesanan);

    Page<PesananPenjualanModel> findPaginated(String sortField, String sortDir);

    List<PesananPenjualanModel> getPesananListByUserAndTanggalBetween(UserModel stafSales, Date tanggalAwal, Date tanggalAkhir);

    List<PesananPenjualanModel> getPesananListForAdminKomplain(int status);

    List<PesananPenjualanModel> getPesananListForStafSales(UserModel user, int status);
}
