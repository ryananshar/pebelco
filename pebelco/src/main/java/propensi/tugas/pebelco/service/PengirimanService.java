package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.Date;
import java.util.List;

public interface PengirimanService {
    List<Pengiriman> findAll();
    Pengiriman findPengirimanById(Long id);
    List<Barang> findAllBarangByIdPengiriman(Long id);
    void updateMetodePengiriman(Long id, Long idMetodePengiriman);
    void updateStatusPengiriman(Long id, int statusPengiriman);
    void terimaPengiriman(Long id, Date tanggalDiterima, String namaPenerima);
    boolean showUbahStatusButton(int status);
    boolean showUbahPengirimanButton(int status);
    void setIsShownFalse(Long id);
}
