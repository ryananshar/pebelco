package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.MetodePengirimanModel;

import java.util.Optional;

@Repository
public interface MetodePengirimanDb extends JpaRepository<MetodePengirimanModel, Long> {
    Optional<MetodePengirimanModel> findByNamaMetodePengiriman(String namaMetodePengiriman);
    Optional<String> findNamaByIdMetode(Long id);
}
