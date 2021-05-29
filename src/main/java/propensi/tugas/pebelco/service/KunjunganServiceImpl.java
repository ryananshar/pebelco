package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.KunjunganModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.KunjunganDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KunjunganServiceImpl implements KunjunganService {
    @Autowired
    KunjunganDb kunjunganDb;

    @Override
    public List<KunjunganModel> getKunjunganListByIsShown(Boolean isShown) {
        return kunjunganDb.findByIsShown(isShown);
    }

    @Override
    public List<KunjunganModel> getKunjunganListByStafSalesByIsShown(UserModel stafSales, Boolean isShown) {
        return kunjunganDb.findByStafSalesAndIsShown(stafSales, isShown);
    }

    @Override
    public KunjunganModel getKunjunganByKodeKunjungan(String kodeKunjungan) {
        return kunjunganDb.findByKodeKunjungan(kodeKunjungan);
    }

    @Override
    public String generateKodeKunjungan(KunjunganModel kunjungan) {
        String idKunjunganString = String.valueOf(kunjungan.getIdKunjungan());
        String kodeKunjungan = "KUN" + idKunjunganString;

        return kodeKunjungan;
    }

    @Override
    public void addKunjungan(KunjunganModel kunjungan) {
        kunjunganDb.save(kunjungan);
        kunjungan.setKodeKunjungan(generateKodeKunjungan(kunjungan));
    }

    @Override
    public KunjunganModel updateKunjungan(KunjunganModel kunjungan) {
        KunjunganModel targetKunjungan = kunjunganDb.findByKodeKunjungan(kunjungan.getKodeKunjungan());

        try {
            targetKunjungan.setNamaToko(kunjungan.getNamaToko());
            targetKunjungan.setAlamatToko(kunjungan.getAlamatToko());
            targetKunjungan.setTanggalKunjungan(kunjungan.getTanggalKunjungan());
            targetKunjungan.setWaktuMulai(kunjungan.getWaktuMulai());
            targetKunjungan.setWaktuSelesai(kunjungan.getWaktuSelesai());

            if (kunjungan.getCatatanKunjungan().equals("")) {
                targetKunjungan.setCatatanKunjungan(null);
            } else {
                targetKunjungan.setCatatanKunjungan(kunjungan.getCatatanKunjungan());
            }

            kunjunganDb.save(targetKunjungan);
            return targetKunjungan;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public void deleteKunjungan(KunjunganModel kunjungan) {
        kunjungan.setIsShown(false);
    }

}
