package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="tag_produk")
public class TagProdukModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag_produk", nullable = false)
    private Long idTagProduk;

    @NotNull
    @Size(max = 30)
    @Column(name = "nama_tag", nullable = false)
    private String namaTag;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "produk_tag", 
        joinColumns = @JoinColumn(name = "id_tag_produk"), 
        inverseJoinColumns = @JoinColumn(name = "id_produk"))
    private List<ProdukModel> listProduk;


    public Long getIdTagProduk() {
        return this.idTagProduk;
    }

    public void setIdTagProduk(Long idTagProduk) {
        this.idTagProduk = idTagProduk;
    }

    public String getNamaTag() {
        return this.namaTag;
    }

    public void setNamaTag(String namaTag) {
        this.namaTag = namaTag;
    }

    public List<ProdukModel> getListProduk() {
        return this.listProduk;
    }

    public void setListProduk(List<ProdukModel> listProduk) {
        this.listProduk = listProduk;
    }

}
