package propensi.tugas.pebelco.service;
import java.util.List;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

public interface TagService {
    List<TagProdukModel> findAll();
    String getNamaTagById(Long id);
    void addTagProduk(List<ProdukModel>produk);
    void addAll(List<TagProdukModel> listTagProduk, Long idProduk);

    void updateTagProduk(List<ProdukModel>produk,ProdukModel produkmodel);

    void deleteTagProdukUpdate(ProdukModel produk);

    void deleteTagProduk(ProdukModel produk);
}
