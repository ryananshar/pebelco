package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.ProdukModel;


@Repository
public interface ProdukDb extends JpaRepository<ProdukModel, Long> {

    ProdukModel findByIdProduk(Long idProduk);

    ProdukModel findByNamaProduk(String namaProduk);

    List<ProdukModel> findAll();

    List<ProdukModel> findByNamaProdukContainingIgnoreCase(String keyword);

//    @Query(value="SELECT * FROM produk WHERE tipe = ?1", nativeQuery = true)
    List<ProdukModel> findByTipe(Integer tipe);


}