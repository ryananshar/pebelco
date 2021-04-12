package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.ProdukModel;

@Repository
public interface ProdukDb extends JpaRepository<ProdukModel, Long> {

}
