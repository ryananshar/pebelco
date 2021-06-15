package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.Date;
import java.util.List;

public interface PengirimanService {
    List<Pengiriman> findAll();
    List<Pengiriman> findAllLaporan();
    Pengiriman findPengirimanByKode(String kodePengiriman);
    List<Barang> findAllBarangByKodePengiriman(String kodePengiriman);
    void updateMetodePengiriman(String kodePengiriman, Long idMetodePengiriman);
    void updateStatusPengiriman(String kodePengiriman, int statusPengiriman);
    void terimaPengiriman(String kodePengiriman, Date tanggalDiterima, String namaPenerima);
    boolean showUbahStatusButton(int status);
    boolean showUbahPengirimanButton(int status);
    void setIsShownFalse(String kodePengiriman);
    List<Pengiriman> getPengirimanByDate(Date startDate, Date finalDate);
    Page<PengirimanModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
}
