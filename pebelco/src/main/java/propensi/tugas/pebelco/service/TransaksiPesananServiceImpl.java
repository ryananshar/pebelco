package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.repository.TransaksiPesananDb;

@Service
@Transactional
public class TransaksiPesananServiceImpl implements TransaksiPesananService{
    @Autowired
    private TransaksiPesananDb transaksiPesananDb;

    @Override
    public void addTransaksiPesanan(TransaksiPesananModel transaksiPesanan) {
        transaksiPesananDb.save(transaksiPesanan);
        
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
