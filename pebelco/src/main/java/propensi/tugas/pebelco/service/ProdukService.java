package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.ProdukModel;

public interface ProdukService {
    List<ProdukModel> findAll();
    ProdukModel getProdukById(Long id);
    void addProduk(ProdukModel produk);
    void deleteProduk(ProdukModel produk);
    void deleteProdukByIdProduk(Long IdProduk);
    ProdukModel updateStokProduk(ProdukModel produk);
}
