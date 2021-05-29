package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.List;

public interface KunjunganService {
    // Method untuk mendapatkan daftar semua Kunjungan berdasarkan isShown
    List<KunjunganModel> getKunjunganListByIsShown(Boolean isShown);

    // Method untuk mendapatkan Kunjungan milik Staf Sales tertentu berdasarkan isShown
    List<KunjunganModel> getKunjunganListByStafSalesByIsShown(UserModel stafSales, Boolean isShown);

    //  Method untuk mendapatkan data berdasarkan Kode Kunjungan
    KunjunganModel getKunjunganByKodeKunjungan(String kodeKunjungan);

    // Method untuk men-generate Kode Kunjungan
    String generateKodeKunjungan(KunjunganModel kunjungan);

    // Method untuk menambah Kunjungan
    void addKunjungan(KunjunganModel kunjungan);

    // Method untuk mengubah Kunjungan
    KunjunganModel updateKunjungan(KunjunganModel kunjungan);

    // Method untuk menghapus Kunjungan
    void deleteKunjungan(KunjunganModel kunjungan);
}
