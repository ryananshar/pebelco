package propensi.tugas.pebelco.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.repository.TagProdukDb;

@Service
@Transactional
public class ProdukServiceImpl implements ProdukService{
    @Autowired
    ProdukDb produkDb;

    @Autowired
    TagProdukDb tagProdukDb;

    @Autowired
    TagService tagService;

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

    @Override
    public void deleteProdukByIdProduk(Long IdProduk){
        produkDb.deleteById(IdProduk);
    }

    @Override
    public ProdukModel updateStokProduk(ProdukModel produk){
        return produkDb.save(produk);
    }

    @Override
    public void addProduk(ProdukModel produk){
        produkDb.save(produk);
    }

    @Override
    public Page<ProdukModel> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.produkDb.findAll(pageable);
    }

}