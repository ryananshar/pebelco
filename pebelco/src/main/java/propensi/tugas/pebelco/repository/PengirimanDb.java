package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.PengirimanModel;

import java.util.List;

@Repository
public interface PengirimanDb extends JpaRepository<PengirimanModel, Long> {
    List<PengirimanModel> findAllByIsShownIsTrue();
}
