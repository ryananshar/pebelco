package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.model.TagProdukModel;

import java.util.List;

@Repository
public interface TagProdukDb extends JpaRepository<TagProdukModel, Long> {
    List<TagProdukModel> findAll();
    TagProdukModel findByIdTagProduk(Long idTag);

    void deleteAllByIdTagProduk(Long id);
    void deleteAllByListProduk(ProdukModel produk);
}
