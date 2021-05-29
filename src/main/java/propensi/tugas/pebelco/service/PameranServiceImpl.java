package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.PameranModel;
import propensi.tugas.pebelco.repository.PameranDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PameranServiceImpl implements PameranService {
    @Autowired
    PameranDb pameranDb;

    @Override
    public List<PameranModel> getPameranList() {
        return pameranDb.findAll();
    }

    @Override
    public PameranModel getPameranById(Long idPameran) {
        return pameranDb.findById(idPameran).get();
    }

    @Override
    public void addPameran(PameranModel pameran) {
        pameranDb.save(pameran);
    }

    @Override
    public PameranModel updatePameran(PameranModel pameran) {
        PameranModel targetPameran = pameranDb.findById(pameran.getIdPameran()).get();

        try {
            targetPameran.setNamaPameran(pameran.getNamaPameran());
            targetPameran.setTanggalMulai(pameran.getTanggalMulai());
            targetPameran.setTanggalSelesai(pameran.getTanggalSelesai());
            targetPameran.setWaktuMulai(pameran.getWaktuMulai());
            targetPameran.setWaktuSelesai(pameran.getWaktuSelesai());
            targetPameran.setTipePameran(pameran.getTipePameran());
            targetPameran.setAlamatPameran(pameran.getAlamatPameran());

            if (pameran.getCatatanPameran().equals("")) {
                targetPameran.setCatatanPameran(null);
            } else {
                targetPameran.setCatatanPameran(pameran.getCatatanPameran());
            }

            pameranDb.save(targetPameran);
            return targetPameran;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public void deletePameran(PameranModel pameran) {
        pameranDb.delete(pameran);
    }
}