package propensi.tugas.pebelco.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="transaksi_komplain")
public class TransaksiKomplainModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi_komplain", nullable = false)
    private Long idTransaksiKomplain;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    // harga satuan x jumlah
    @NotNull
    @Column(name = "harga", nullable = false)
    private Long harga;

    // deskripsi
    @Size(max = 250)
    @Column(name = "deskripsiKomplain", nullable = true)
    private String deskripsiKomplain;

    // id komplain
    @ManyToOne
    @JoinColumn(name = "id_komplain", referencedColumnName = "id_komplain")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KomplainModel komplainTransaksi;

    // id produk
    @ManyToOne
    @JoinColumn(name = "id_produk", referencedColumnName = "id_produk")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProdukModel produkKomplain;

    public Long getIdTransaksiKomplain() {
        return this.idTransaksiKomplain;
    }

    public void setIdTransaksiKomplain(Long idTransaksiKomplain) {
        this.idTransaksiKomplain = idTransaksiKomplain;
    }

    public KomplainModel getKomplainTransaksi() {
        return this.komplainTransaksi;
    }

    public void setKomplainTransaksi(KomplainModel komplainTransaksi) {
        this.komplainTransaksi = komplainTransaksi;
    }

    public ProdukModel getProdukKomplain() {
        return this.produkKomplain;
    }

    public void setProdukKomplain(ProdukModel produkKomplain) {
        this.produkKomplain = produkKomplain;
    }

    public Integer getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Long getHarga() {
        return this.harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getDeskripsiKomplain() {
        return this.deskripsiKomplain;
    }

    public void setDeskripsiKomplain(String deskripsiKomplain) {
        this.deskripsiKomplain = deskripsiKomplain;
    }

}
