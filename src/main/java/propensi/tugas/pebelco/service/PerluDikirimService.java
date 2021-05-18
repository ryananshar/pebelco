package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.MetodePengirimanModel;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirim;

public interface PerluDikirimService {
    List<PerluDikirim> findAll();
    PerluDikirim findKomplainByKode(String kodeKomplain);
    PerluDikirim findPesananByKode(String kodePesanan);
    List<MetodePengirimanModel> findAllMetodePengiriman();
    List<Barang> findAllBarangByKodeKomplain(String kodeKomplain);
    List<Barang> findAllBarangByKodePesanan(String kodePesanan);
    void addPengirimanKomplain(String kodeKomplain, Long idMetodePengiriman);
    void addPengirimanPesanan(String kodePesanan, Long idMetodePengiriman);
}
