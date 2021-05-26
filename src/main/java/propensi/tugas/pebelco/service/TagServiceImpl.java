package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TagProdukModel;
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
    private TagService tagService;

    @Autowired
    private ProdukDb produkDb;


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
    @Override
    public String getNamaTagById(Long id){
        return tagProdukDb.findByIdTagProduk(id).getNamaTag();
    }

    @Override
    public void addTagProduk(List<ProdukModel>produk){
        TagProdukModel tag= new TagProdukModel();
        for(int i=0; i<produk.size();i++){
            for(int j=0; j<produk.get(i).getListTagProduk().size();j++){
                tag=tagProdukDb.findByIdTagProduk(produk.get(i).getListTagProduk().get(j).getIdTagProduk());
            }
            tag.getListProduk().add(produk.get(i));
            produk.remove(i);
            i--;
        }
    }

    @Override
    public void updateTagProduk(List<ProdukModel>produk,ProdukModel produkmodel){
        TagProdukModel tag= new TagProdukModel();
        for(int i=0; i<produk.size();i++){
            for(int j=0; j<produk.get(i).getListTagProduk().size();j++){
                tag=tagProdukDb.findByIdTagProduk(produk.get(i).getListTagProduk().get(j).getIdTagProduk());
            }
            tag.getListProduk().add(produk.get(i));
            produk.remove(i);
            i--;
        }
    }

    @Override
    public void deleteTagProduk(ProdukModel produk) {
        List<TagProdukModel> tag=tagService.findAll();
        Long idproduk=produk.getIdProduk();
        for(int i=0; i<tag.size();i++){
            for(int j=0; j<tag.get(i).getListProduk().size();j++) {
                Long id = tag.get(i).getListProduk().get(j).getIdProduk();
                if(idproduk==id) {
                    tag.get(i).getListProduk().remove(j);
                    j--;
                }
            }
        }
        produkDb.delete(produk);
    }

    @Override
    public void deleteTagProdukUpdate(ProdukModel produk) {
        List<TagProdukModel> tag=tagService.findAll();
        Long idproduk=produk.getIdProduk();
        for(int i=0; i<tag.size();i++){
            for(int j=0; j<tag.get(i).getListProduk().size();j++) {
                Long id = tag.get(i).getListProduk().get(j).getIdProduk();
                if(idproduk==id) {
                    tag.get(i).getListProduk().remove(j);
                    j--;
                }
            }
        }
    }

    @Override
    public TagProdukModel getTagbyId(Long id) {
        return tagProdukDb.findByIdTagProduk(id);
    }

//    @Override
//    public void deleteListTagProduk(List<TagProdukModel>list){
//        tagProdukDb.deleteAllByListProduk(list);
//    }
}