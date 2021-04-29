package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.PesananPenjualanDb;

@Service
@Transactional
public class PesananPenjualanServiceImpl implements PesananPenjualanService{
    @Autowired
    private PesananPenjualanDb pesananPenjualanDb;

    @Override
    public void addPesanan(PesananPenjualanModel pesananPenjualan) {
        pesananPenjualanDb.save(pesananPenjualan);
        
    }

    @Override
    public List<PesananPenjualanModel> getPesananList(Boolean bolean) {
        return pesananPenjualanDb.findByIsShown(bolean);
        // to do
        // pesanan list pagination
    }

    @Override
    public PesananPenjualanModel getPesananByIdPesanan(Long idPesananPenjualan) {
        return pesananPenjualanDb.findByIdPesananPenjualan(idPesananPenjualan);
    }

    @Override
    public PesananPenjualanModel updatePesanan(PesananPenjualanModel pesananPenjualan) {
        return pesananPenjualanDb.save(pesananPenjualan);
    }

    @Override
    public void deletePesanan(PesananPenjualanModel pesananPenjualan) throws Exception {
        // pesananPenjualanDb.deleteById(pesananPenjualan.getIdPesananPenjualan());
        pesananPenjualan.setIsShown(false);
        pesananPenjualanDb.save(pesananPenjualan);        
    }

    @Override
    public Long calculateTotal(List<TransaksiPesananModel> barangPesanan, Integer diskon) {
        Long tempTotal = 0L;
        for (TransaksiPesananModel barang : barangPesanan) {
            tempTotal += barang.getHarga();
        }
        Long total = (Long) tempTotal*(100 - diskon)/100;
        return total;
    }

    @Override
    public PesananPenjualanModel getPesananByKodePesanan(String kodePesananPenjualan) {
        return pesananPenjualanDb.findByKodePesananPenjualan(kodePesananPenjualan);
    }

    @Override
    public List<PesananPenjualanModel> getPesananListByUser(UserModel user, Boolean bolean) {
        return pesananPenjualanDb.findByUserAndIsShown(user, bolean);
    }
    
}
