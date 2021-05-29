package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.Date;
import java.util.List;

@Repository
public interface KunjunganDb extends JpaRepository<KunjunganModel,Long> {
    KunjunganModel findByKodeKunjungan(String kodeKunjungan);
    List<KunjunganModel> findByIsShown(Boolean isShown);
    List<KunjunganModel> findByStafSalesAndIsShown(UserModel stafSales, Boolean isShown);
    List<KunjunganModel> findByStafSalesAndTanggalKunjunganBetween(UserModel stafSales, Date tanggalAwal, Date tanggalAkhir);
}
