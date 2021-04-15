package propensi.tugas.pebelco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import propensi.tugas.pebelco.model.ProdukModel;

public interface ProdukDb extends JpaRepository<ProdukModel, Long>{
    ProdukModel findByIdProduk(Long idProduk);

    List<ProdukModel> findAll();

}
