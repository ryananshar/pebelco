package propensi.tugas.pebelco.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.UserModel;

public interface NotifikasiDb extends JpaRepository<NotifikasiModel, Long>{
    List<NotifikasiModel> findByIdPenerimaAndIsNotif(Long idPenerima, Boolean isNotif);

    List<NotifikasiModel> findByIdPenerimaOrIdRoleAndIsNotifOrderByWaktuDibuatDesc(Long idPenerima, Long idRole, Boolean isNotif);

    List<NotifikasiModel> findByIdPenerimaOrIdRoleAndWaktuDibuatBetweenOrderByWaktuDibuatDesc(Long idPenerima, Long idRole, Date waktuAkhir, Date waktuAwal);

    List<NotifikasiModel> findByUserAndWaktuDibuatBetweenOrderByWaktuDibuatDesc(UserModel user, Date waktuAkhir, Date waktuAwal);
}
