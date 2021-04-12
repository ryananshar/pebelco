package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.repository.ProdukDb;

@Service
@Transactional
public class ProdukServiceImpl implements ProdukService{
    @Autowired
    private ProdukDb produkDb;

    @Override
    public List<ProdukModel> findAll() {
        return produkDb.findAll();
    }

    @Override
    public ProdukModel getProdukById(Long id){
        return produkDb.findById(id).get();
    }

    @Override
    public void deleteProduk(ProdukModel produk) {
        produkDb.delete(produk);
    }

}
