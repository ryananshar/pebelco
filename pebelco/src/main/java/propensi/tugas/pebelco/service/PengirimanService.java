package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.List;

public interface PengirimanService {
    List<Pengiriman> findAll();
    Pengiriman findPengirimanById(Long id);
    List<Barang> findAllBarangByIdPengiriman(Long id);
}
