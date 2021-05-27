package propensi.tugas.pebelco.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.UserModel;

@Repository
public interface LaporanPengirimanDb extends JpaRepository<PengirimanModel, Long>{
    List<PengirimanModel> findAll();

    PengirimanModel findByKodePengiriman(String kodePengiriman);
}
