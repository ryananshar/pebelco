package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

public interface PesananPenjualanService {
    //  Method untuk menambah objek
    void addPesanan(PesananPenjualanModel pesananPenjualan);

    //  Method untuk mendapatkan list data yang telah tersimpan
    List<PesananPenjualanModel> getPesananList();

    //  Method untuk mendapatkan data berdasarkan id
    PesananPenjualanModel getPesananByIdPesanan(Long idPesananPenjualan);

    // Method untuk update 
    PesananPenjualanModel updatePesanan(PesananPenjualanModel pesananPenjualan);

    // Method untuk delete 
    void deletePesanan(PesananPenjualanModel pesananPenjualan) throws Exception;

    Long calculateTotal(List<TransaksiPesananModel> barangPesanan, Integer diskon);
}
