package propensi.tugas.pebelco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import propensi.tugas.pebelco.model.NotifikasiModel;

public interface NotifikasiDb extends JpaRepository<NotifikasiModel, Long>{
    List<NotifikasiModel> findByIdPenerimaAndIsNotif(Long idPenerima, Boolean isNotif);

    List<NotifikasiModel> findByIdPenerimaOrIdRoleAndIsNotifOrderByWaktuDibuatDesc(Long idPenerima, Long idRole, Boolean isNotif);
}
