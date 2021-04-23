package propensi.tugas.pebelco.service;
import java.util.List;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

public interface TagService {
    List<TagProdukModel> findAll();
    void addAll(List<TagProdukModel> listTagProduk, Long idProduk);
}
