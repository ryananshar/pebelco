package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.repository.TagProdukDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService{
    @Autowired
    private TagProdukDb tagProdukDb;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private TagService tagService;


    @Override
    public List<TagProdukModel> findAll() {
        return tagProdukDb.findAll();
    }

    @Override
    public void addAll(List<TagProdukModel> listTagProduk, Long idProduk) {
        for (TagProdukModel tagProduk : listTagProduk) {

            tagProduk.setIdTagProduk(idProduk);
            tagProdukDb.save(tagProduk);
        }

    }
}
