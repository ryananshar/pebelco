package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.List;

public interface KomplainService{
    void addKomplain(KomplainModel komplain);
    KomplainModel updateKomplain(KomplainModel komplain);
    KomplainModel getKomplainByKodeKomplain(String kodeKomplain);
    List<KomplainModel> getListKomplain();
    List<KomplainModel> getListKomplainByUser(UserModel user);
    void deleteKomplain(KomplainModel komplain);
    void changeStatusDisetujui(KomplainModel komplain);
    void changeStatusDitolak(KomplainModel komplain);
}