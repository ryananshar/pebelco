package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.MetodePengirimanModel;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirim;

public interface PerluDikirimService {
    List<PerluDikirim> findAll();
    PerluDikirim findKomplainById(Long id);
    PerluDikirim findPesananById(Long id);
    List<MetodePengirimanModel> findAllMetodePengiriman();
    List<Barang> findAllBarangByIdKomplain(Long id);
    List<Barang> findAllBarangByIdPesanan(Long id);
}
