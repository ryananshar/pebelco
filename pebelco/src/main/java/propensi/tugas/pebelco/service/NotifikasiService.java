package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.NotifikasiModel;

public interface NotifikasiService {
    void addNotifikasi(NotifikasiModel notifikasi);

    List<NotifikasiModel> getNotifListByUserAndRole(Long idPenerima, Long idRole, Boolean isNotif);
}
