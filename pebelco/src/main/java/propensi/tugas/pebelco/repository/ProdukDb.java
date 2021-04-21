package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.ProdukModel;

<<<<<<< HEAD
@Repository
public interface ProdukDb extends JpaRepository<ProdukModel, Long> {
=======

@Repository
public interface ProdukDb extends JpaRepository<ProdukModel, Long> {

    ProdukModel findByIdProduk(Long idProduk);

    ProdukModel findByNamaProduk(String namaProduk);

    List<ProdukModel> findAll();


>>>>>>> 2aec39eee8e83fa17e0d9506a1505913c2c3800b
}
