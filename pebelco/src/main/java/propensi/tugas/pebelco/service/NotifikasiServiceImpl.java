package propensi.tugas.pebelco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.repository.NotifikasiDb;

public class NotifikasiServiceImpl implements NotifikasiService{
    @Autowired
    private NotifikasiDb notifikasiDb;

    @Override
    public void addNotifikasi(NotifikasiModel notifikasi) {
        notifikasi.setIsNotif(true);
        notifikasiDb.save(notifikasi);
        
    }

    @Override
    public List<NotifikasiModel> getNotifListByUserAndRole(Long idPenerima, Long idRole, Boolean isNotif) {
        return notifikasiDb.findByIdPenerimaOrIdRoleAndIsNotifOrderByWaktuDibuatDesc(idPenerima, idRole, isNotif);
    }
    
}
