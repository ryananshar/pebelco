package propensi.tugas.pebelco.service;

import java.util.List;

import org.springframework.data.domain.Page;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;

public interface ProdukService {
    List<ProdukModel> findAll();
    Page<ProdukModel> findPaginated(String sortField, String sortDir);
    ProdukModel getProdukById(Long id);
    void addProduk(ProdukModel produk);
    void deleteProduk(ProdukModel produk);
    void deleteProdukByIdProduk(Long IdProduk);
    ProdukModel updateStokProduk(ProdukModel produk);
    List<ProdukModel> findBySearch(String keyword);
    List<ProdukModel> getProdukByTipe(Integer tipe);
}