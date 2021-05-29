package propensi.tugas.pebelco.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.PengirimanModel;

import java.util.List;

@Repository
public interface PengirimanDb extends JpaRepository<PengirimanModel, Long> {
    List<PengirimanModel> findAllByIsShownIsTrue();

    PengirimanModel findByKodePengiriman(String kodePengiriman);

    List<PengirimanModel>findByTanggalDiterimaBetween(Date startDate, Date finalDate);
}
