package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.repository.TransaksiPesananDb;

@Service
@Transactional
public class TransaksiPesananServiceImpl implements TransaksiPesananService{
    @Autowired
    private TransaksiPesananDb transaksiPesananDb;

    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Override
    public void saveAll(List<TransaksiPesananModel> listTransaksiPesanan) {
        transaksiPesananDb.saveAll(listTransaksiPesanan);        
    }

    @Override
    public void addAll(List<TransaksiPesananModel> listTransaksiPesanan, Long idPesanan) {
        for (TransaksiPesananModel transaksiPesanan : listTransaksiPesanan) {
            transaksiPesanan.setPesananTransaksi(pesananPenjualanService.getPesananByIdPesanan(idPesanan));
            transaksiPesananDb.save(transaksiPesanan);
        }
        
    }   

    @Override
    public List<TransaksiPesananModel> getTransaksiPesananList() {
        return transaksiPesananDb.findAll();
    }

    @Override
    public TransaksiPesananModel getByIdTransaksiPesanan(Long idTransaksiPesanan) {
        return transaksiPesananDb.findByIdTransaksiPesanan(idTransaksiPesanan);
    }

    @Override
    public TransaksiPesananModel updateTransaksiPesanan(TransaksiPesananModel transaksiPesanan) {
        return transaksiPesananDb.save(transaksiPesanan);
    }

    @Override
    public void deleteTransaksiPesanan(Long idTransaksiPesanan) {
        transaksiPesananDb.deleteById(idTransaksiPesanan);        
    }

}
