package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.KomplainDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KomplainServiceImpl implements KomplainService{
    @Autowired
    KomplainDb komplainDb;

    @Override
    public void addKomplain(KomplainModel komplain){
        komplain.setKodeKomplain("KOM" + komplain.getIdKomplain());
        komplainDb.save(komplain);
    }

    @Override
    public KomplainModel updateKomplain(KomplainModel komplain){
        return komplainDb.save(komplain);
    }

    @Override
    public List<KomplainModel> getListKomplain(){
        return komplainDb.findByIsShownOrderByIdKomplainAsc(true);
    }

    @Override
    public List<KomplainModel> getListKomplainByUser(UserModel user){
        return komplainDb.findByUserAndIsShownOrderByIdKomplainAsc(user, true);
    }

    @Override
    public KomplainModel getKomplainByKodeKomplain(String kodeKomplain){
        return komplainDb.findByKodeKomplain(kodeKomplain);
    }

    @Override
    public void deleteKomplain(KomplainModel komplain){
        komplain.setIsShown(false);
    }

    @Override
    public void changeStatusDisetujui(KomplainModel komplain){
        komplain.setStatusKomplain(1);
    }

    @Override
    public void changeStatusDitolak(KomplainModel komplain){
        komplain.setStatusKomplain(2);
    }
}
