package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.ProdukModel;

public interface ProdukService {
    List<ProdukModel> findAll();
    ProdukModel getProdukById(Long id);
    void deleteProduk(ProdukModel produk);
}
