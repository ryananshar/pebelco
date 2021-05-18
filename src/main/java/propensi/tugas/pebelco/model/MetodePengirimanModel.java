package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="metode_pengiriman")
public class MetodePengirimanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metode", nullable = false)
    private Long idMetode;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_metode_pengiriman", nullable = false)
    private String namaMetodePengiriman;

    @OneToMany(mappedBy = "metodePengiriman", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PengirimanModel> listPengiriman;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_pengiriman", referencedColumnName = "idPengiriman", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private PengirimanModel pengiriman;

    public Long getIdMetode() {
        return this.idMetode;
    }

    public void setIdMetode(Long idMetode) {
        this.idMetode = idMetode;
    }

    public String getNamaMetodePengiriman() {
        return this.namaMetodePengiriman;
    }

    public void setNamaMetodePengiriman(String namaMetodePengiriman) {
        this.namaMetodePengiriman = namaMetodePengiriman;
    }

    public List<PengirimanModel> getListPengiriman() {
        return this.listPengiriman;
    }

    public void setListPengiriman(List<PengirimanModel> listPengiriman) {
        this.listPengiriman = listPengiriman;
    }

}
