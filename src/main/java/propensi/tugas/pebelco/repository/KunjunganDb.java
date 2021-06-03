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
    List<KunjunganModel> findByIsShownOrderByIdKunjunganAsc(Boolean isShown);
    List<KunjunganModel> findByStafSalesAndIsShownOrderByIdKunjunganAsc(UserModel stafSales, Boolean isShown);
    List<KunjunganModel> findByStafSalesAndTanggalKunjunganBetweenOrderByIdKunjunganAsc(UserModel stafSales, Date tanggalAwal, Date tanggalAkhir);
}
