package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;

import org.springframework.data.domain.Page;

public interface ProdukService {
    List<ProdukModel> findAll();
    ProdukModel getProdukById(Long id);
    void addProduk(ProdukModel produk);
    void deleteProduk(ProdukModel produk);
    void deleteProdukByIdProduk(Long IdProduk);
    ProdukModel updateStokProduk(ProdukModel produk);

    //percobaan pagination

    Page<ProdukModel> findPaginated(int pageNo, int pageSize);

}