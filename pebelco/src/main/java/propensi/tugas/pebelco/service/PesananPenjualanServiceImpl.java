package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
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
    public List<PesananPenjualanModel> getPesananList() {
        return pesananPenjualanDb.findAll();
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
        pesananPenjualanDb.deleteById(pesananPenjualan.getIdPesananPenjualan());        
    }
    
}
