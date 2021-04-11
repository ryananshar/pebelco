package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.PesananPenjualanModel;

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
}
