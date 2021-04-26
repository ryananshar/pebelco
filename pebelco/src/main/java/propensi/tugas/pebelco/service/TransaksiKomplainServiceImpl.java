package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import propensi.tugas.pebelco.model.TransaksiKomplainModel;
import propensi.tugas.pebelco.repository.TransaksiKomplainDb;

import java.util.List;

@Service
@Transactional
public class TransaksiKomplainServiceImpl implements TransaksiKomplainService {
    @Autowired
    TransaksiKomplainDb transaksiKomplainDb;

    @Override
    public void addTransaksiKomplain(TransaksiKomplainModel transaksiKomplain) {
        transaksiKomplainDb.save(transaksiKomplain);
    }

    @Override
    public TransaksiKomplainModel getTransaksiByIdTransaksi(Long idTransaksiKomplain){
        return transaksiKomplainDb.findByIdTransaksiKomplain(idTransaksiKomplain);
    }

    @Override
    public  TransaksiKomplainModel updateTransaksiKomplain(TransaksiKomplainModel transaksiKomplain){
        return transaksiKomplainDb.save(transaksiKomplain);
    }

    @Override
    public List<TransaksiKomplainModel> getListTransaksiKomplain(){
        return transaksiKomplainDb.findAll();
    }
}