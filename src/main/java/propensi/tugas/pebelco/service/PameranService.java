package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.PameranModel;

import java.util.List;

public interface PameranService {
    // Method untuk mendapatkan daftar semua Pameran
    List<PameranModel> getPameranList();

    // Method untuk mengambil Pameran berdasarkan Id Pameran
    PameranModel getPameranById(Long idPameran);

    // Method untuk menambah Pameran
    void addPameran(PameranModel pameran);

    // Method untuk mengubah Pameran
    PameranModel updatePameran(PameranModel pameran);

    // Method untuk menghapus Pameran
    void deletePameran(PameranModel pameran);
}

